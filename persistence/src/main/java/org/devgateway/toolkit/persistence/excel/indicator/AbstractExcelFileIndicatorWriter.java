package org.devgateway.toolkit.persistence.excel.indicator;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractExcelFileIndicatorWriter {

    public static final int INT_NUM_FORMAT = BuiltinFormats.getBuiltinFormat("0");
    public static final int REAL_NUM_FORMAT = BuiltinFormats.getBuiltinFormat("0.0");

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected int rowNo = 0;

    protected XSSFCellStyle integerCellStyle;
    protected XSSFCellStyle doubleCellStyle;

    public AbstractExcelFileIndicatorWriter() {
    }

    public void write(OutputStream outputStream) throws IOException {
        this.workbook = new XSSFWorkbook();

        String sheetName = getSheetName();
        this.sheet =  sheetName == null ? this.workbook.createSheet() : this.workbook.createSheet(sheetName);

        addStyles();
        writeTableHeader();
        writeTableBody();

        workbook.write(outputStream);
    }

    protected XSSFRow createRow() {
        return sheet.createRow(this.rowNo++);
    }

    protected void addIntegerStyle() {
        integerCellStyle = workbook.createCellStyle();
        integerCellStyle.setDataFormat(INT_NUM_FORMAT);
    }

    protected void addDoubleStyle() {
        doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.setDataFormat(REAL_NUM_FORMAT);
    }

    protected String getSheetName() {
        return null;
    }

    protected abstract void addStyles();
    protected abstract void writeTableHeader();
    protected abstract void writeTableBody();

}
