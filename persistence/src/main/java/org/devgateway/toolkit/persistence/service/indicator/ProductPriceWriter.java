package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceWriter {

    public static final int DEPARTMENT_COL_IDX = 0;
    public static final int MARKET_COL_IDX = 1;
    public static final int DATE_COL_IDX = 2;
    public static final int PRODUCT_COL_IDX = 3;

    public static final int DATE_DATA_FORMAT = BuiltinFormats.getBuiltinFormat("d-mmm-yy");
    public static final int INT_NUM_FORMAT = BuiltinFormats.getBuiltinFormat("0");
    public static final int REAL_NUM_FORMAT = BuiltinFormats.getBuiltinFormat("0.00");

    public static final int DEPARTMENT_WIDTH = 15 * 256;
    public static final int MARKET_WIDTH = 22 * 256;
    public static final int DATE_WIDTH = 10 * 256;
    public static final int PRODUCT_WIDTH = 25 * 256;

    private final List<Product> products;

    private final boolean productsOnSeparateRows;

    private XSSFCellStyle dateCellStyle;
    private XSSFCellStyle integerCellStyle;
    private XSSFCellStyle quantityCellStyle;

    private ProductPriceColumns cols;

    public ProductPriceWriter(List<Product> products, boolean productsOnSeparateRows) {
        this.products = new ArrayList<>(products);
        Collections.sort(this.products);

        this.productsOnSeparateRows = productsOnSeparateRows;
    }

    public void write(Collection<ProductPrice> prices, Collection<ProductQuantity> quantities, int year,
            OutputStream outputStream) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(DATE_DATA_FORMAT);

        integerCellStyle = workbook.createCellStyle();
        integerCellStyle.setDataFormat(INT_NUM_FORMAT);

        quantityCellStyle = workbook.createCellStyle();
        quantityCellStyle.setDataFormat(REAL_NUM_FORMAT);

        writeTableHeader(sheet);

        writeTableBody(prices, quantities, year, sheet);

        workbook.write(outputStream);
    }

    private void writeTableBody(Collection<ProductPrice> prices, Collection<ProductQuantity> quantities, int year,
            XSSFSheet sheet) {
        int rowNum = 1;

        Map<Pair<Date, Market>, List<ProductPrice>> groupedPrices =
                prices.stream().collect(groupingBy(p -> priceToDateMarket(p, year)));

        Map<Pair<Date, Market>, List<ProductQuantity>> groupedQuantities =
                quantities.stream().collect(groupingBy(q -> quantityToDateMarket(q, year)));

        TreeSet<Pair<Date, Market>> keys = new TreeSet<>();
        keys.addAll(groupedPrices.keySet());
        keys.addAll(groupedQuantities.keySet());

        for (Pair<Date, Market> key : keys) {
            if (productsOnSeparateRows) {
                for (Product product : products) {
                    List<ProductPrice> filteredPrices = groupedPrices.get(key).stream()
                            .filter(v -> v.getProduct().equals(product))
                            .collect(toList());

                    if (!filteredPrices.isEmpty()) {

                        XSSFRow row = createRow(sheet, rowNum++, key);

                        row.createCell(PRODUCT_COL_IDX).setCellValue(product.getName());

                        for (ProductPrice price : filteredPrices) {
                            Integer idx = cols.getPriceTypeCol(price.getPriceType());
                            if (price.getPrice() != null && idx != null) {
                                XSSFCell cell = row.createCell(idx);
                                cell.setCellValue(price.getPrice());
                                cell.setCellStyle(integerCellStyle);
                            }
                        }
                    }
                }
            } else {
                XSSFRow row = createRow(sheet, rowNum++, key);

                for (ProductPrice price : groupedPrices.getOrDefault(key, ImmutableList.of())) {
                    Integer idx = cols.getProductAndPriceTypeCol(price.getProduct(), price.getPriceType());
                    if (price.getPrice() != null && idx != null) {
                        XSSFCell cell = row.createCell(idx);
                        cell.setCellValue(price.getPrice());
                        cell.setCellStyle(integerCellStyle);
                    }
                }

                for (ProductQuantity quantity : groupedQuantities.getOrDefault(key, ImmutableList.of())) {
                    Integer idx = cols.getQuantityCol(quantity.getProduct());
                    if (quantity.getQuantity() != null && idx != null) {
                        XSSFCell cell = row.createCell(idx);
                        cell.setCellValue(quantity.getQuantity().doubleValue());
                        cell.setCellStyle(getQuantityCellStyle(quantity.getProduct()));
                    }
                }
            }
        }
    }

    private XSSFCellStyle getQuantityCellStyle(Product product) {
        if (product.getUnit().getName().equals(MeasurementUnit.HEAD)) {
            return integerCellStyle;
        } else {
            return quantityCellStyle;
        }
    }

    private void writeTableHeader(XSSFSheet sheet) {
        sheet.setColumnWidth(DEPARTMENT_COL_IDX, DEPARTMENT_WIDTH);
        sheet.setColumnWidth(MARKET_COL_IDX, MARKET_WIDTH);
        sheet.setColumnWidth(DATE_COL_IDX, DATE_WIDTH);

        sheet.setDefaultColumnStyle(DATE_COL_IDX, dateCellStyle);

        XSSFRow hrow = sheet.createRow(0);
        hrow.createCell(DEPARTMENT_COL_IDX).setCellValue("Département");
        hrow.createCell(MARKET_COL_IDX).setCellValue("Marché de collecte");
        hrow.createCell(DATE_COL_IDX).setCellValue("Date de collecte");

        cols = new ProductPriceColumns(productsOnSeparateRows, products);

        if (productsOnSeparateRows) {
            hrow.createCell(PRODUCT_COL_IDX).setCellValue("Product");
            sheet.setColumnWidth(PRODUCT_COL_IDX, PRODUCT_WIDTH);

            List<PriceType> priceTypes = products.stream()
                    .flatMap(p -> p.getPriceTypes().stream())
                    .distinct()
                    .collect(toList());

            for (PriceType priceType : priceTypes) {
                int col = cols.getPriceTypeCol(priceType);
                hrow.createCell(col).setCellValue(ProductPriceColumns.getPriceTypeColumnName(priceType));
                sheet.setDefaultColumnStyle(col, integerCellStyle);
            }
        } else {
            int col;
            for (Product product : products) {
                col = cols.getQuantityCol(product);
                hrow.createCell(col).setCellValue(ProductPriceColumns.getQuantityColumnName(product));
                sheet.setDefaultColumnStyle(col, getQuantityCellStyle(product));

                for (PriceType priceType : product.getPriceTypes()) {
                    col = cols.getProductAndPriceTypeCol(product, priceType);
                    hrow.createCell(col)
                            .setCellValue(ProductPriceColumns.getProductAndPriceTypeColumnName(product, priceType));
                    sheet.setDefaultColumnStyle(col, integerCellStyle);
                }
            }
        }
    }

    private Pair<Date, Market> priceToDateMarket(ProductPrice price, int year) {
        Date date = Date.valueOf(LocalDate.of(year,
                price.getMonthDay().getMonth(),
                price.getMonthDay().getDayOfMonth()));

        return Pair.of(date, price.getMarket());
    }

    private Pair<Date, Market> quantityToDateMarket(ProductQuantity quantity, int year) {
        Date date = Date.valueOf(LocalDate.of(year,
                quantity.getMonthDay().getMonth(),
                quantity.getMonthDay().getDayOfMonth()));

        return Pair.of(date, quantity.getMarket());
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
}
