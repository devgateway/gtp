package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceWriter {

    public static final int DEPARTMENT_COL_IDX = 0;
    public static final int MARKET_COL_IDX = 1;
    public static final int DATE_COL_IDX = 2;
    public static final int PRODUCT_COL_IDX = 3;

    public static final int DATE_DATA_FORMAT = BuiltinFormats.getBuiltinFormat("d-mmm-yy");

    public static final int DEPARTMENT_WIDTH = 15 * 256;
    public static final int MARKET_WIDTH = 22 * 256;
    public static final int DATE_WIDTH = 10 * 256;
    public static final int PRODUCT_WIDTH = 25 * 256;

    private final List<Product> products;

    private final boolean productsOnSeparateRows;

    private XSSFCellStyle dateCellStyle;

    public ProductPriceWriter(List<Product> products, boolean productsOnSeparateRows) {
        this.products = new ArrayList<>(products);
        Collections.sort(this.products);

        this.productsOnSeparateRows = productsOnSeparateRows;
    }

    public void write(Collection<ProductPrice> prices, int year, OutputStream outputStream) throws IOException {
        writeMarketData(outputStream, getMarketData(prices, year));
    }

    private void writeMarketData(OutputStream outputStream,
            Map<Pair<Date, Market>, Set<Value>> data) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(DATE_DATA_FORMAT);

        sheet.setDefaultColumnStyle(DATE_COL_IDX, dateCellStyle);

        Map<Value, Integer> columnIndex = writeTableHeader(sheet);

        writeTableBody(data, sheet, columnIndex);

        workbook.write(outputStream);
    }

    private void writeTableBody(Map<Pair<Date, Market>, Set<Value>> data, XSSFSheet sheet,
            Map<Value, Integer> columnIndex) {
        int rowNum = 1;
        for (Map.Entry<Pair<Date, Market>, Set<Value>> dataEntry : data.entrySet()) {

            Set<Value> values = dataEntry.getValue();

            if (productsOnSeparateRows) {
                for (Product product : products) {
                    Set<Value> productValues = values.stream()
                            .filter(v -> v.product.equals(product))
                            .collect(Collectors.toSet());

                    if (!productValues.isEmpty()) {

                        XSSFRow row = createRow(sheet, rowNum++, dataEntry.getKey());

                        row.createCell(PRODUCT_COL_IDX).setCellValue(product.getName());

                        for (Value value : productValues) {
                            row.createCell(PRODUCT_COL_IDX + 1 + columnIndex.get(value)).setCellValue(value.price);
                        }
                    }
                }
            } else {
                XSSFRow row = createRow(sheet, rowNum++, dataEntry.getKey());
                for (Value value : values) {
                    if (value.price != null) {
                        row.createCell(DATE_COL_IDX + 1 + columnIndex.get(value)).setCellValue(value.price);
                    }
                }
            }
        }
    }

    private Map<Value, Integer> writeTableHeader(XSSFSheet sheet) {
        Map<Value, Integer> columnIndex = new HashMap<>();

        sheet.setColumnWidth(DEPARTMENT_COL_IDX, DEPARTMENT_WIDTH);
        sheet.setColumnWidth(MARKET_COL_IDX, MARKET_WIDTH);
        sheet.setColumnWidth(DATE_COL_IDX, DATE_WIDTH);

        XSSFRow hrow = sheet.createRow(0);
        hrow.createCell(DEPARTMENT_COL_IDX).setCellValue("Department / Département");
        hrow.createCell(MARKET_COL_IDX).setCellValue("Collection market / Marché de collecte");
        hrow.createCell(DATE_COL_IDX).setCellValue("Date of collection / Date de collecte");

        if (productsOnSeparateRows) {
            hrow.createCell(PRODUCT_COL_IDX).setCellValue("Product");
            sheet.setColumnWidth(PRODUCT_COL_IDX, PRODUCT_WIDTH);

            List<PriceType> priceTypes = products.stream()
                    .flatMap(p -> p.getPriceTypes().stream())
                    .distinct()
                    .sorted()
                    .collect(toList());

            for (int i = 0; i < priceTypes.size(); i++) {
                PriceType priceType = priceTypes.get(i);
                for (Product product : products) {
                    columnIndex.put(new Value(product, priceType, null), i);
                }
                hrow.createCell(PRODUCT_COL_IDX + 1 + i).setCellValue(priceType.getLabel());
            }
        } else {
            int i = 0;
            for (Product product : products) {
                List<PriceType> priceTypes = new ArrayList<>(product.getPriceTypes());
                Collections.sort(priceTypes);
                for (PriceType priceType : priceTypes) {
                    hrow.createCell(DATE_COL_IDX + 1 + i)
                            .setCellValue(product.getName() + " - " + priceType.getLabel());
                    columnIndex.put(new Value(product, priceType, null), i);
                    i++;
                }
            }
        }
        return columnIndex;
    }

    private Map<Pair<Date, Market>, Set<Value>> getMarketData(Collection<ProductPrice> prices, int year) {
        Map<Pair<Date, Market>, Set<Value>> data = new TreeMap<>();

        for (ProductPrice price : prices) {
            Date date = Date.valueOf(LocalDate.of(year,
                    price.getMonthDay().getMonth(),
                    price.getMonthDay().getDayOfMonth()));

            Pair<Date, Market> dateMarket = Pair.of(date, price.getMarket());

            Value value = new Value(price.getProduct(), price.getPriceType(), price.getPrice());
            data.computeIfAbsent(dateMarket, dm -> new HashSet<>()).add(value);
        }

        return data;
    }

    private XSSFRow createRow(XSSFSheet sheet, int rowNum, Pair<Date, Market> dateMarket) {
        Date date = dateMarket.getLeft();
        Market market = dateMarket.getRight();

        XSSFRow row = sheet.createRow(rowNum);

        String dptName = market.getDepartment().getName();
        row.createCell(DEPARTMENT_COL_IDX).setCellValue(dptName);

        String mkName = market.getName();
        row.createCell(MARKET_COL_IDX).setCellValue(mkName);

        XSSFCell dateCell = row.createCell(DATE_COL_IDX);
        dateCell.setCellValue(date);
        dateCell.setCellStyle(dateCellStyle);

        return row;
    }

    private static class Value {

        private final Product product;
        private final PriceType priceType;
        private final Integer price;

        Value(Product product, PriceType priceType, Integer price) {
            this.product = product;
            this.priceType = priceType;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Value)) {
                return false;
            }
            Value value = (Value) o;
            return product.equals(value.product)
                    && priceType.equals(value.priceType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(product, priceType);
        }
    }
}
