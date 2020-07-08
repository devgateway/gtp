package org.devgateway.toolkit.persistence.excel.indicator;

import com.google.common.collect.ImmutableList;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractExcelFileIndicatorReader<T> {

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;

    protected LinkedHashSet<String> errors;

    public T read(InputStream is) throws ReaderException {
        try {
            this.workbook = new XSSFWorkbook(is);
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

    protected String errorAt(XSSFCell cell, String text) {
        return text + " à " + cell.getReference();
    }

    protected boolean isEmpty(XSSFCell cell) {
        return cell == null || cell.getRawValue() == null;
    }

    protected abstract void readHeader();
    protected abstract T readContent();
}
