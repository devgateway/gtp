package org.devgateway.toolkit.persistence.excel.indicator;

import com.google.common.collect.ImmutableList;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.indicator.SearchableCollection;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractExcelFileIndicatorReader<T> {

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected FormulaEvaluator evaluator;
    protected int rowNo = 0;

    protected LinkedHashSet<String> errors;

    public T read(InputStream is) throws ReaderException {
        try {
            this.workbook = new XSSFWorkbook(is);
            this.evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
        } catch (IOException e) {
            throw new ReaderException(ImmutableList.of("Erreur d'E / S."), e);
        } catch (UnsupportedFileFormatException e) {
            throw new ReaderException(ImmutableList.of("Format de fichier non pris en charge. "
                    + "Veuillez utiliser un fichier Microsoft Excel Open XML Format Spreadsheet (XLSX)."), e);
        }

        this.workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        this.sheet = workbook.getSheetAt(0);

        this.errors = new LinkedHashSet<>();

        readHeader();

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        T data = readContent();

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }
        return data;
    }

    protected boolean matchesLong(XSSFCell cell, Long expected) {
        Object value = getValue(cell);
        if (value != null) {
            if (value instanceof Number) {
                Number number = (Number) value;
                if (Math.floor(number.doubleValue()) == number.doubleValue()) {
                    return number.longValue() == expected;
                }
            } else if (value instanceof String) {
                try {
                    return expected.equals(Long.parseLong(((String) value).trim()));
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    protected boolean matchesString(XSSFCell cell, String expected) {
        String value = getAsString(cell);
        boolean matched = value != null && SearchableCollection.normalize(value)
                .equals(SearchableCollection.normalize(expected));
        return matched;
    }

    protected String getAsString(XSSFCell cell) {
        Object value = isEmpty(cell) ? null : getValue(cell);
        return value == null ? null : value.toString();
    }

    protected Object getValue(XSSFCell cell) {
        switch (cell.getCellType()) {
            case BLANK:
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return cell.getNumericCellValue();
            case BOOLEAN: return cell.getBooleanCellValue();
            case ERROR: return cell.getErrorCellString();
            case FORMULA: return this.evaluator.evaluate(cell).formatAsString();
            default:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getRawValue();
        }

    }

    protected XSSFRow nextRow() {
        return sheet.getRow(this.rowNo++);
    }

    protected void addErrorAt(XSSFCell cell, String text) {
        errors.add(errorAt(cell, text));
    }

    protected String errorAt(XSSFCell cell, String text) {
        return text + " à " + cell.getReference();
    }

    protected boolean isEmpty(XSSFCell cell) {
        return cell == null || cell.getRawValue() == null;
    }

    protected abstract void readHeader();
    protected abstract T readContent();
}
