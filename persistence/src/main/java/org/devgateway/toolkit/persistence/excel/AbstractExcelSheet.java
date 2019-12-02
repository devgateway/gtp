package org.devgateway.toolkit.persistence.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Class that prepares the default Styles and Fonts for Excel cells.
 *
 * @author idobre
 * @since 13/11/2017
 */
public abstract class AbstractExcelSheet implements ExcelSheet {
    protected final Workbook workbook;

    private Font dataFont;

    private Font headerFont;

    private Font introFont;

    private Font linkFont;

    private final CellStyle dataStyleCell;

    private final CellStyle headerStyleCell;

    private final CellStyle introStyleCell;

    private final CreationHelper createHelper;

    // declare only one cell object reference
    private Cell cell;

    public AbstractExcelSheet(final Workbook workbook) {
        this.workbook = workbook;

        // get the styles from workbook without creating them again (by default the workbook has already 1 style)
        if (workbook.getNumCellStyles() > 1) {
            this.dataStyleCell = workbook.getCellStyleAt((short) 1);
            this.headerStyleCell = workbook.getCellStyleAt((short) 2);
            this.introStyleCell = workbook.getCellStyleAt((short) 3);
        } else {
            // init the fonts and styles
            this.dataFont = this.workbook.createFont();
            this.dataFont.setFontHeightInPoints((short) 12);
            this.dataFont.setFontName("Times New Roman");
            this.dataFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

            this.headerFont = this.workbook.createFont();
            this.headerFont.setFontHeightInPoints((short) 12);
            this.headerFont.setFontName("Times New Roman");
            this.headerFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            this.headerFont.setBold(true);

            this.introFont = this.workbook.createFont();
            this.introFont.setFontHeightInPoints((short) 14);
            this.introFont.setFontName("Times New Roman");
            this.introFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            this.introFont.setBold(true);

            this.dataStyleCell = this.workbook.createCellStyle();
            this.dataStyleCell.setAlignment(HorizontalAlignment.LEFT);
            this.dataStyleCell.setVerticalAlignment(VerticalAlignment.CENTER);
            this.dataStyleCell.setWrapText(true);
            this.dataStyleCell.setFont(this.dataFont);

            this.headerStyleCell = this.workbook.createCellStyle();
            this.headerStyleCell.setAlignment(HorizontalAlignment.CENTER);
            this.headerStyleCell.setVerticalAlignment(VerticalAlignment.CENTER);
            this.headerStyleCell.setWrapText(true);
            this.headerStyleCell.setFont(this.headerFont);

            this.introStyleCell = this.workbook.createCellStyle();
            this.introStyleCell.setAlignment(HorizontalAlignment.LEFT);
            this.introStyleCell.setVerticalAlignment(VerticalAlignment.TOP);
            this.introStyleCell.setWrapText(true);
            this.introStyleCell.setFont(this.introFont);
        }

        this.createHelper = workbook.getCreationHelper();
    }

    private void writeCell(final String value, final Row row, final int column) {
        if (value != null) {
            cell = row.createCell(column, CellType.STRING);
            cell.setCellValue(value);
        }
    }

    /**
     * Creates a cell and tries to determine it's type based on the value type.
     * <p>
     * There is only one Cell object otherwise the Heap Space will fill really quickly.
     *
     * @param value
     * @param row
     * @param column
     */
    @Override
    public void writeCell(final Object value, final Row row, final int column) {
        // try to determine the cell type based on the object value
        // if nothing matches then use 'CellType.EMPTY_STRING' as type and call the object toString() function.
        //      * don't create any cell if the value is null (Cell.CELL_TYPE_BLANK)
        //      * do nothing if we have an empty List/Set instead of display empty brackets like [ ]
        if (value != null && !((value instanceof List || value instanceof Set) && ((Collection) value).isEmpty())) {
            if (value instanceof String) {
                cell = row.createCell(column, CellType.STRING);
                cell.setCellValue((String) value);
            } else {
                if (value instanceof Integer) {
                    cell = row.createCell(column, CellType.NUMERIC);
                    cell.setCellValue((Integer) value);
                } else {
                    if (value instanceof BigDecimal) {
                        cell = row.createCell(column, CellType.NUMERIC);
                        cell.setCellValue(((BigDecimal) value).doubleValue());
                    } else {
                        if (value instanceof Boolean) {
                            cell = row.createCell(column, CellType.BOOLEAN);
                            cell.setCellValue(((Boolean) value) ? "Yes" : "No");
                        } else {
                            if (value instanceof Date) {
                                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                cell = row.createCell(column);
                                cell.setCellValue(sdf.format((Date) value));
                            } else {
                                cell = row.createCell(column, CellType.STRING);
                                cell.setCellValue(value.toString());
                            }
                        }
                    }
                }
            }
            if (row.getRowNum() >= 1) {
                cell.setCellStyle(dataStyleCell);
            } else {
                cell.setCellStyle(introStyleCell);
            }
        } else {
            // create a CellType.BLANK
            row.createCell(column);
        }
    }

    /**
     * Create a header cell with a particular style.
     *
     * @param value
     * @param row
     * @param column
     */
    protected void writeHeaderCell(final Object value, final Row row, final int column) {
        this.writeCell(value, row, column);
        cell.setCellStyle(headerStyleCell);
    }


    /**
     * Create a new row and set the default height (different heights for headers and data rows).
     *
     * @param sheet
     * @param rowNumber
     * @return Row
     */
    protected Row createRow(final Sheet sheet, final int rowNumber) {
        final Row row = sheet.createRow(rowNumber);

        if (rowNumber < 1) {
            row.setHeight((short) 1500);             // 75px (1500 / 10 / 2)
        } else {
            row.setHeight((short) 500);              // 25px  (600 / 10 / 2)
        }

        return row;
    }

    /**
     * Get the last 'free' cell of a {@link Row}.
     *
     * @param row
     */
    protected int getFreeColl(final Row row) {
        return row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
    }
}
