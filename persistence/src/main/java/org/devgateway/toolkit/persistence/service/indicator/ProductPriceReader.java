package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.location.Department;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceReader {

    private static final String[] DATE_PATTERNS = { "dd-MM-yyyy", "dd-MMM-yyyy" };

    private final SearchableCollection<Department> departments;

    private final Map<Department, SearchableCollection<Market>> marketsByDepartment;

    private final SearchableCollection<Product> products;

    private final boolean productsOnSeparateRows;

    private List<String> errors;

    private int firstPriceColIdx;

    public ProductPriceReader(List<Product> products, List<Department> departments,
            List<Market> markets, boolean productsOnSeparateRows) {

        this.departments = new SearchableCollection<>(departments, Department::getName);

        this.products = new SearchableCollection<>(products, Product::getName);

        marketsByDepartment = departments.stream()
                .collect(toMap(Function.identity(),
                        d -> new SearchableCollection<>(markets.stream()
                                .filter(m->m.getDepartment().equals(d))
                                .collect(toList()), Market::getName)));

        this.productsOnSeparateRows = productsOnSeparateRows;
    }

    public Collection<ProductPrice> read(int year, InputStream is) throws ReaderException {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new ReaderException(ImmutableList.of("Erreur d'E / S."), e);
        } catch (UnsupportedFileFormatException e) {
            throw new ReaderException(ImmutableList.of("Format de fichier non pris en charge. "
                    + "Veuillez utiliser un fichier Microsoft Excel Open XML Format Spreadsheet (XLSX)."), e);
        }

        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        XSSFSheet sheet = workbook.getSheetAt(0);

        errors = new ArrayList<>();

        List<Pair<Product, PriceType>> columnDefinitions = readHeader(sheet);

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        List<ProductPrice> productPrices = readPrices(year, sheet, columnDefinitions);

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        return productPrices;
    }

    private List<ProductPrice> readPrices(int year, XSSFSheet sheet, List<Pair<Product, PriceType>> columnDefinitions) {
        int numColumns = firstPriceColIdx + columnDefinitions.size();

        List<ProductPrice> productPrices = new ArrayList<>();

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);

            if (isEmptyRow(row, numColumns)) {
                continue;
            }

            Department department = getDepartment(row);
            Market market = getMarket(row, department);
            MonthDay monthDay = getMonthDay(year, row);

            // for each product or product x price type
            Product product = null;
            if (productsOnSeparateRows) {
                product = getProduct(row);
            }

            for (int i = 0; i < columnDefinitions.size(); i++) {
                Pair<Product, PriceType> columnDefinition = columnDefinitions.get(i);
                if (columnDefinition != null) {
                    XSSFCell priceCell = row.getCell(firstPriceColIdx + i);
                    if (!isEmpty(priceCell)) {
                        Integer price = getPrice(priceCell);
                        if (!productsOnSeparateRows) {
                            product = columnDefinition.getKey();
                        }
                        PriceType priceType = columnDefinition.getValue();

                        productPrices.add(new ProductPrice(product, market, monthDay, priceType, price));
                    }
                }
            }
        }

        return productPrices;
    }

    private Product getProduct(XSSFRow row) {
        XSSFCell cell = row.getCell(ProductPriceWriter.PRODUCT_COL_IDX);
        if (isEmpty(cell)) {
            errors.add(errorAt(cell, "Produit non spécifié"));
            return null;
        }

        String productName;
        try {
            productName = cell.getStringCellValue();
        } catch (Exception e) {
            errors.add(errorAt(cell, "Produit invalide"));
            return null;
        }

        Product product = products.get(productName);
        if (product == null) {
            errors.add(errorAt(cell, "Produit inconnu"));
        }

        return product;
    }

    private MonthDay getMonthDay(int year, XSSFRow row) {
        XSSFCell dateCell = row.getCell(ProductPriceWriter.DATE_COL_IDX);
        if (isEmpty(dateCell)) {
            errors.add(errorAt(dateCell, "Date non précisée"));
            return null;
        }

        Date date;
        if (dateCell.getCellType() == CellType.STRING) {
            try {
                date = parseDate(dateCell.getStringCellValue());
            } catch (ParseException e) {
                errors.add(errorAt(dateCell, "Could not parse date"));
                return null;
            }
        } else {
            try {
                date = dateCell.getDateCellValue();
            } catch (NumberFormatException e) {
                errors.add(errorAt(dateCell, "Impossible d'analyser la date"));
                return null;
            }
        }

        ZonedDateTime zonedDate = date.toInstant().atZone(ZoneId.systemDefault());
        if (Year.from(zonedDate).getValue() != year) {
            errors.add(errorAt(dateCell, "Date prévue pour " + year + " mais trouvée " + date));
            return null;
        }

        return MonthDay.from(zonedDate);
    }

    private static Date parseDate(String val) throws ParseException {
        try {
            return DateUtils.parseDate(val, Locale.FRENCH, DATE_PATTERNS);
        } catch (ParseException e) {
            return DateUtils.parseDate(val, Locale.ENGLISH, DATE_PATTERNS);
        }
    }

    private Market getMarket(XSSFRow row, Department department) {
        if (department == null) {
            return null;
        }

        XSSFCell marketCell = row.getCell(ProductPriceWriter.MARKET_COL_IDX);
        if (isEmpty(marketCell)) {
            errors.add(errorAt(marketCell, "Marché non spécifié"));
            return null;
        }

        String marketName;
        try {
            marketName = marketCell.getStringCellValue();
        } catch (Exception e) {
            errors.add(errorAt(marketCell, "Marché invalide"));
            return null;
        }
        Market market = marketsByDepartment.get(department).get(marketName);
        if (market == null) {
            errors.add(errorAt(marketCell, "Marché inconnu " + marketName
                    + ". Si le marché existe, veuillez vérifier le type de marché."));
            return null;
        }

        return market;
    }

    private Department getDepartment(XSSFRow row) {
        XSSFCell dptCell = row.getCell(ProductPriceWriter.DEPARTMENT_COL_IDX);
        if (isEmpty(dptCell)) {
            errors.add(errorAt(dptCell, "Département non spécifié"));
            return null;
        }

        String departmentName;
        try {
            departmentName = dptCell.getStringCellValue();
        } catch (Exception e) {
            errors.add(errorAt(dptCell, "Département invalide"));
            return null;
        }

        Department department = departments.get(departmentName);
        if (department == null) {
            errors.add(errorAt(dptCell, "Département inconnu " + departmentName));
            return null;
        }

        return department;
    }

    private Integer getPrice(XSSFCell priceCell) {
        try {
            int price = (int) priceCell.getNumericCellValue();
            if (price < 0) {
                errors.add(errorAt(priceCell, "Prix négatif non autorisé"));
            }
            return price;
        } catch (IllegalStateException | NumberFormatException e) {
            errors.add(errorAt(priceCell, "Impossible d'analyser le prix"));
            return null;
        }
    }

    private List<Pair<Product, PriceType>> readHeader(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);

        SearchableCollection<Pair<Product, PriceType>> allColumnDefs;

        if (productsOnSeparateRows) {
            allColumnDefs = new SearchableCollection<>(
                    products.elements.values().stream()
                            .flatMap(p -> p.getPriceTypes().stream())
                            .distinct()
                            .map(pt -> Pair.of((Product) null, pt))
                            .collect(toList()),
                    p -> p.getValue().getLabel());

            firstPriceColIdx = ProductPriceWriter.PRODUCT_COL_IDX + 1;
        } else {
            allColumnDefs = new SearchableCollection<>(
                    products.elements.values().stream()
                            .flatMap(p -> p.getPriceTypes().stream().sorted().map(pt -> Pair.of(p, pt)))
                            .collect(toList()),
                    p -> p.getKey().getName() + " - " + p.getValue().getLabel());

            firstPriceColIdx = ProductPriceWriter.DATE_COL_IDX + 1;
        }

        List<Pair<Product, PriceType>> columnDefinitions = new ArrayList<>();

        for (int i = firstPriceColIdx; i < row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell(i);

            String colName = null;
            if (!isEmpty(cell)) {
                try {
                    colName = cell.getStringCellValue().replace(" – ", " - ");
                } catch (Exception e) {
                    errors.add(errorAt(cell, "Valeur d'en-tête non valide"));
                }
            }

            Pair<Product, PriceType> productPriceTypePair;
            productPriceTypePair = colName != null ? allColumnDefs.get(colName) : null;

            if (colName != null && productPriceTypePair == null) {
                errors.add(errorAt(cell, "Valeur d'en-tête inattendue"));
            }

            if (productPriceTypePair != null && columnDefinitions.contains(productPriceTypePair)) {
                errors.add(errorAt(cell, "En-tête en double"));
            }

            columnDefinitions.add(productPriceTypePair);
        }

        return columnDefinitions;
    }

    private String errorAt(XSSFCell cell, String text) {
        return text + " à " + cell.getReference();
    }

    private boolean isEmptyRow(XSSFRow row, int numColumns) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < numColumns; i++) {
            if (!isEmpty(row.getCell(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(XSSFCell cell) {
        return cell.getRawValue() == null;
    }

    private static class SearchableCollection<T> {

        private final Map<String, T> elements;

        SearchableCollection(Collection<T> col, Function<T, String> nameFn) {
            elements = col.stream().collect(Collectors.toMap(e -> normalize(nameFn.apply(e)), Function.identity()));
        }

        private String normalize(String value) {
            return StringUtils.stripAccents(StringUtils.strip(value.toLowerCase()));
        }

        T get(String name) {
            return elements.get(normalize(name));
        }
    }
}
