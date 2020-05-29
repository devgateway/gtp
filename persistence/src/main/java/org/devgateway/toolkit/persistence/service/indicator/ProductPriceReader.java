package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.InputStream;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.UnsupportedFileFormatException;
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

    private final SearchableCollection<Department> departments;

    private final Map<Department, SearchableCollection<Market>> marketsByDepartment;

    private final List<Product> products;

    private final boolean productsOnSeparateRows;

    public ProductPriceReader(List<Product> products,
            List<Market> markets, boolean productsOnSeparateRows) {

        List<Department> departmentList = markets.stream().map(Market::getDepartment)
                .distinct()
                .collect(toList());
        departments = new SearchableCollection<>(departmentList, Department::getName);

        this.products = new ArrayList<>(products);
        Collections.sort(this.products);

        marketsByDepartment = departmentList.stream()
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
            throw new ReaderException(ImmutableList.of("I/O error."), e);
        } catch (UnsupportedFileFormatException e) {
            throw new ReaderException(ImmutableList.of("Unsupported file format. Please use a "
                    + "Microsoft Excel Open XML Format Spreadsheet (XLSX) file."), e);
        }

        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        XSSFSheet sheet = workbook.getSheetAt(0);

        List<String> errors = new ArrayList<>();

        List<Pair<Product, PriceType>> columnDefinitions = products.stream()
                .flatMap(p -> p.getPriceTypes().stream().sorted().map(pt -> Pair.of(p, pt)))
                .collect(toList());

        // check header
        if (productsOnSeparateRows) {
            throw new UnsupportedOperationException();
        } else {
            int rowNum = 0;
            XSSFRow row = sheet.getRow(rowNum);
            for (int i = 0; i < columnDefinitions.size(); i++) {
                Pair<Product, PriceType> colDef = columnDefinitions.get(i);
                String expectedColName = colDef.getKey().getName() + " / " + colDef.getValue().getLabel();

                int colNum = ProductPriceWriter.DATE_COL_IDX + 1 + i;
                XSSFCell cell = row.getCell(colNum);
                if (isEmpty(cell)) {
                    errors.add(errorAt(cell,
                            String.format("Expected header '%s' but found no value", expectedColName)));
                    continue;
                }

                String colName;
                try {
                    colName = cell.getStringCellValue();
                } catch (Exception e) {
                    errors.add(errorAt(cell,
                            String.format("Expected header '%s' but found an invalid value", expectedColName)));
                    continue;
                }

                if (!colName.equalsIgnoreCase(expectedColName)) {
                    errors.add(errorAt(cell,
                            String.format("Expected header '%s' but found '%s'", expectedColName, colName)));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        List<ProductPrice> productPrices = new ArrayList<>();

        int rowNum = 1;
        for (;; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);

            if (row == null) {
                break;
            }

            XSSFCell dptCell = row.getCell(ProductPriceWriter.DEPARTMENT_COL_IDX);
            if (isEmpty(dptCell)) {
                break;
            }
            String departmentName = dptCell.getStringCellValue();
            Department department = departments.get(departmentName);
            if (department == null) {
                errors.add(errorAt(dptCell, "Unknown department " + departmentName));
                continue;
            }

            XSSFCell marketCell = row.getCell(ProductPriceWriter.MARKET_COL_IDX);
            if (isEmpty(marketCell)) {
                errors.add(errorAt(marketCell, "Market not specified"));
                continue;
            }
            String marketName = marketCell.getStringCellValue();
            Market market = marketsByDepartment.get(department).get(marketName);
            if (market == null) {
                errors.add(errorAt(marketCell, "Unknown market " + marketName));
                continue;
            }

            XSSFCell dateCell = row.getCell(ProductPriceWriter.DATE_COL_IDX);
            if (isEmpty(dateCell)) {
                errors.add(errorAt(dateCell, "Date not specified"));
                continue;
            }

            Date date;
            try {
                date = dateCell.getDateCellValue();
            } catch (IllegalStateException | NumberFormatException e) {
                errors.add(errorAt(dateCell, "Could not parse date"));
                continue;
            }
            ZonedDateTime zonedDate = date.toInstant().atZone(ZoneId.systemDefault());
            if (Year.from(zonedDate).getValue() != year) {
                errors.add(errorAt(dateCell, "Expected a date for " + year + " year but found " + date));
                continue;
            }
            MonthDay monthDay = MonthDay.from(zonedDate);

            // for each product or product x price type
            if (productsOnSeparateRows) {
                throw new UnsupportedOperationException();
            } else {
                int colNum = ProductPriceWriter.DATE_COL_IDX;
                for (Pair<Product, PriceType> columnDefinition : columnDefinitions) {
                    XSSFCell priceCell = row.getCell(++colNum);
                    if (!isEmpty(priceCell)) {

                        double price;
                        try {
                            price = priceCell.getNumericCellValue();
                        } catch (IllegalStateException | NumberFormatException e) {
                            errors.add(errorAt(priceCell, "Could not parse price"));
                            continue;
                        }

                        Product product = columnDefinition.getKey();
                        PriceType priceType = columnDefinition.getValue();

                        productPrices.add(new ProductPrice(product, market, monthDay, priceType, (int) price));
                    }
                }
            }
        }

        int lastRowNum = sheet.getLastRowNum();
        int numColumns;
        if (productsOnSeparateRows) {
            throw new UnsupportedOperationException();
        } else {
            numColumns = ProductPriceWriter.DATE_COL_IDX + columnDefinitions.size() + 1;
        }
        for (;rowNum <= lastRowNum; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            for (int col = 0; col < numColumns; col++) {
                XSSFCell cell = row.getCell(col);
                if (!isEmpty(cell)) {
                    errors.add(errorAt(cell, "Found a value after last imported row"));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        return productPrices;
    }

    private String errorAt(XSSFCell cell, String text) {
        return text + " at " + cell.getReference();
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
            return StringUtils.stripAccents(StringUtils.strip(value));
        }

        T get(String name) {
            return elements.get(normalize(name));
        }
    }
}
