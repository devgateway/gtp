package org.devgateway.toolkit.persistence.service.indicator.river;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;

import com.google.common.collect.ImmutableList;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelReader {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    public <L extends IRiverLevel> Collection<L> read(InputStream is,
            BiFunction<MonthDay, BigDecimal, L> creator) throws ReaderException {

        XSSFWorkbook sheets;
        try {
            sheets = new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new ReaderException(ImmutableList.of("Erreur d'E / S."), e);
        } catch (UnsupportedFileFormatException e) {
            throw new ReaderException(ImmutableList.of("Format de fichier non pris en charge. "
                    + "Veuillez utiliser un fichier Microsoft Excel Open XML Format Spreadsheet (XLSX)."), e);
        }

        Set<L> data = new TreeSet<>();

        List<String> errors = new ArrayList<>();

        XSSFSheet sheet = sheets.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int r = 1; r < lastRowNum; r++) {
            XSSFRow row = sheet.getRow(r);

            XSSFCell dateCell = row.getCell(0);
            XSSFCell levelCell = row.getCell(1);

            boolean dateIsMissing = (dateCell == null || dateCell.getRawValue() == null);
            boolean levelIsMissing = (levelCell == null || levelCell.getRawValue() == null);

            if (dateIsMissing && levelIsMissing) {
                continue;
            }
            if (dateIsMissing) {
                errors.add(String.format("Date non spécifiée sur la ligne %d.", (r + 1)));
                continue;
            }

            MonthDay monthDay;
            try {
                if (dateCell.getCellType() == CellType.STRING) {
                    String strVal = dateCell.getStringCellValue();
                    monthDay = MonthDay.from(formatter.parse(strVal.substring(0, Math.min(strVal.length(), 5))));
                } else {
                    Date dateCellValue = dateCell.getDateCellValue();
                    monthDay = MonthDay.from(dateCellValue.toInstant().atZone(ZoneId.systemDefault()));
                }
            } catch (DateTimeException | NumberFormatException e) {
                errors.add(String.format("Date non valide sur la ligne %d.", (r + 1)));
                continue;
            }

            BigDecimal level = null;
            if (!levelIsMissing) {
                level = readLevel(levelCell);
                if (level == null) {
                    errors.add(String.format("Niveau de rivière non valide sur la ligne %d.", (r + 1)));
                    continue;
                }
            }

            L monthDayLevel = creator.apply(monthDay, level);

            if (data.contains(monthDayLevel)) {
                errors.add(String.format("Dupliquer la date %s sur la ligne %d.", formatter.format(monthDay), (r + 1)));
            } else if (level != null) {
                data.add(monthDayLevel);
            }
        }

        if (!errors.isEmpty()) {
            throw new ReaderException(errors);
        }

        return data;
    }

    private BigDecimal readLevel(XSSFCell cell) {
        try {
            double levelAsDouble = cell.getNumericCellValue();
            if (levelAsDouble < 0) {
                return null;
            }

            BigDecimal level = new BigDecimal(levelAsDouble).setScale(1, RoundingMode.HALF_EVEN);
            if (level.precision() > 10) {
                return null;
            }
            return level;
        } catch (IllegalStateException | NumberFormatException e) {
            return null;
        }
    }
}
