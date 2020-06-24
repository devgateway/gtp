package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
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
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
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

    private LinkedHashSet<String> errors;

    private ProductPriceColumns cols;

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

    public ProductYearlyPrices read(int year, InputStream is) throws ReaderException {
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

        errors = new LinkedHashSet<>();

        readHeader(sheet);

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        ProductYearlyPrices yearlyPrices = readPrices(year, sheet);

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        return yearlyPrices;
    }

    private ProductYearlyPrices readPrices(int year, XSSFSheet sheet) {
        int lastColumn = cols.getLastColumn();

        ProductYearlyPrices yearlyPrices = new ProductYearlyPrices();

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);

            if (isEmptyRow(row, lastColumn)) {
                continue;
            }

            Department department = getDepartment(row);
            Market market = getMarket(row, department);
            MonthDay monthDay = getMonthDay(year, row);

            if (market == null || monthDay == null) {
                continue;
            }

            if (productsOnSeparateRows) {
                Product product = getProduct(row);
                if (product != null) {
                    for (PriceType priceType : product.getPriceTypes()) {
                        XSSFCell priceCell = getOptionalCell(row, cols.getPriceTypeCol(priceType));
                        processPriceCell(row, priceCell, yearlyPrices, market, monthDay, product, priceType);
                    }
                }
            } else {
                for (Product product :  products.elements.values()) {
                    for (PriceType priceType : product.getPriceTypes()) {
                        XSSFCell priceCell = getOptionalCell(row, cols.getProductAndPriceTypeCol(product, priceType));
                        processPriceCell(row, priceCell, yearlyPrices, market, monthDay, product, priceType);
                    }
                }

                for (Product product :  products.elements.values()) {
                    XSSFCell qtCell = getOptionalCell(row, cols.getQuantityCol(product));
                    if (!isEmpty(qtCell)) {
                        BigDecimal quantity = getQuantity(qtCell, product);
                        ProductQuantity productQuantity = new ProductQuantity(product, market, monthDay, quantity);
                        boolean isNew = yearlyPrices.addQuantity(productQuantity);
                        if (!isNew) {
                            reportDuplicateRowError(row, product, market, monthDay);
                        }
                    }
                }
            }
        }

        return yearlyPrices;
    }

    private XSSFCell getOptionalCell(XSSFRow row, Integer col) {
        return col != null ? row.getCell(col) : null;
    }

    private void processPriceCell(XSSFRow row, XSSFCell priceCell, ProductYearlyPrices yearlyPrices, Market market,
            MonthDay monthDay, Product product, PriceType priceType) {
        if (!isEmpty(priceCell)) {
            Integer price = getPrice(priceCell);
            ProductPrice productPrice = new ProductPrice(product, market, monthDay, priceType, price);
            boolean isNew = yearlyPrices.addPrice(productPrice);
            if (!isNew) {
                reportDuplicateRowError(row, product, market, monthDay);
            }
        }
    }

    private void reportDuplicateRowError(XSSFRow row, Product product, Market market, MonthDay monthDay) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM", Locale.FRENCH);
        errors.add(errorAt(row.getCell(0), String.format("Ligne en double pour le produit %s, le marché %s, le jour %s",
                product.getLabel(), market.getLabel(), fmt.format(monthDay))));
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
                errors.add(errorAt(dateCell, "Les cellules de texte doivent utiliser le format "
                        + DATE_PATTERNS[0] + " ou " + DATE_PATTERNS[1]));
                return null;
            }
        } else {
            if (DateUtil.isCellDateFormatted(dateCell)) {
                try {
                    date = dateCell.getDateCellValue();
                } catch (NumberFormatException e) {
                    errors.add(errorAt(dateCell, "Impossible d'analyser la date"));
                    return null;
                }
            } else {
                errors.add(errorAt(dateCell, "La cellule doit utiliser un format de date"));
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
            int price = (int) Math.round(priceCell.getNumericCellValue());
            if (price < 0) {
                errors.add(errorAt(priceCell, "Prix négatif non autorisé"));
            }
            return price;
        } catch (IllegalStateException | NumberFormatException e) {
            errors.add(errorAt(priceCell, "Impossible d'analyser le prix"));
            return null;
        }
    }

    private BigDecimal getQuantity(XSSFCell cell, Product product) {
        try {
            double qt = cell.getNumericCellValue();
            if (qt < 0) {
                errors.add(errorAt(cell, "Quantité négative non autorisée"));
            }
            BigDecimal val;
            if (product.getUnit().getName().equals(MeasurementUnit.HEAD)) {
                val = new BigDecimal(Math.round(qt));
            } else {
                val = new BigDecimal(String.format("%.1f", qt));
            }
            if (val.precision() > 10) {
                errors.add(errorAt(cell, "Veuillez utiliser au plus 10 chiffres"));
            }
            return val;
        } catch (IllegalStateException | NumberFormatException e) {
            errors.add(errorAt(cell, "Impossible d'analyser le quantité"));
            return null;
        }
    }

    private void readHeader(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);

        SearchableCollection<Pair<Product, PriceType>> productAndPriceTypeNames;
        SearchableCollection<Product> quantityNames;
        SearchableCollection<PriceType> priceTypeNames;

        cols = new ProductPriceColumns(productsOnSeparateRows);

        int firstPriceColIdx;

        if (productsOnSeparateRows) {
            productAndPriceTypeNames = null;

            quantityNames = null;

            List<PriceType> priceTypes = products.elements.values().stream()
                    .flatMap(p -> p.getPriceTypes().stream())
                    .distinct()
                    .collect(toList());

            priceTypeNames = new SearchableCollection<>(priceTypes, ProductPriceColumns::getPriceTypeColumnName);

            firstPriceColIdx = ProductPriceWriter.PRODUCT_COL_IDX + 1;
        } else {
            productAndPriceTypeNames = new SearchableCollection<>(
                    products.elements.values().stream()
                            .flatMap(p -> p.getPriceTypes().stream().sorted().map(pt -> Pair.of(p, pt)))
                            .collect(toList()),
                    p -> p.getKey().getName() + " - " + p.getValue().getLabel());

            quantityNames = new SearchableCollection<>(products.elements.values(),
                    ProductPriceColumns::getQuantityColumnName);

            priceTypeNames = null;

            firstPriceColIdx = ProductPriceWriter.DATE_COL_IDX + 1;
        }

        for (int i = firstPriceColIdx; i < row.getLastCellNum(); i++) {
            XSSFCell cell = row.getCell(i);

            String colName = null;
            if (!isEmpty(cell)) {
                try {
                    colName = cell.getStringCellValue();
                } catch (Exception e) {
                    errors.add(errorAt(cell, "Valeur d'en-tête non valide"));
                }
            }
            if (colName == null) {
                continue;
            }

            try {
                if (productsOnSeparateRows) {
                    PriceType priceType = priceTypeNames.get(colName);
                    if (priceType != null) {
                        cols.addPriceTypeCol(priceType, i);
                    } else {
                        errors.add(errorAt(cell, "Valeur d'en-tête inattendue"));
                    }
                } else {
                    Product product = quantityNames.get(colName);
                    if (product != null) {
                        cols.addQuantityCol(product, i);
                    }

                    Pair<Product, PriceType> productAndPriceType;
                    productAndPriceType = productAndPriceTypeNames.get(colName);
                    if (productAndPriceType != null) {
                        cols.addPriceCol(productAndPriceType.getKey(), productAndPriceType.getValue(), i);
                    }

                    if (product == null && productAndPriceType == null) {
                        errors.add(errorAt(cell, "Valeur d'en-tête inattendue"));
                    }
                }
            } catch (DuplicateElementException e) {
                errors.add(errorAt(cell, "En-tête en double"));
            }
        }
    }

    private String errorAt(XSSFCell cell, String text) {
        return text + " à " + cell.getReference();
    }

    private boolean isEmptyRow(XSSFRow row, int lastColumn) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i <= lastColumn; i++) {
            if (!isEmpty(row.getCell(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(XSSFCell cell) {
        return cell == null || cell.getRawValue() == null;
    }

    private static class SearchableCollection<T> {

        private final Map<String, T> elements;

        SearchableCollection(Collection<T> col, Function<T, String> nameFn) {
            elements = col.stream().collect(Collectors.toMap(e -> normalize(nameFn.apply(e)), Function.identity()));
        }

        private String normalize(String value) {
            return StringUtils.stripAccents(StringUtils.normalizeSpace(StringUtils.strip(value.toLowerCase())))
                    .replace('–', '-');
        }

        T get(String name) {
            return elements.get(normalize(name));
        }
    }
}
