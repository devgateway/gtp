package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverLevelReference;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelReader {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    public Collection<RiverLevelReference> read(HydrologicalYear year, InputStream is,
            Supplier<RiverLevelReference> creator) throws RiverLevelReaderException {

        XSSFWorkbook sheets;
        try {
            sheets = new XSSFWorkbook(is);
        } catch (IOException e) {
            throw new RiverLevelReaderException(ImmutableList.of("Invalid XLSX file format."), e);
        }

        Set<RiverLevelReference> data = new TreeSet<>();

        List<String> errors = new ArrayList<>();

        XSSFSheet sheet = sheets.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int r = 1; r < lastRowNum; r++) {
            XSSFRow row = sheet.getRow(r);

            RiverLevelReference level = creator.get();

            XSSFCell dateCell = row.getCell(0);
            MonthDay monthDay;
            if (dateCell == null || dateCell.getRawValue() == null) {
                continue;
            }

            try {
                if (dateCell.getCellType() == CellType.STRING) {
                    String strVal = dateCell.getStringCellValue();
                    monthDay = MonthDay.from(formatter.parse(strVal.substring(0, Math.min(strVal.length(), 5))));
                } else {
                    Date dateCellValue = dateCell.getDateCellValue();
                    monthDay = MonthDay.from(dateCellValue.toInstant().atZone(ZoneId.systemDefault()));
                }
                level.setMonthDay(monthDay);
            } catch (DateTimeException | NumberFormatException e) {
                errors.add(String.format("Invalid date on row %d.", (r + 1)));
                continue;
            }

            XSSFCell levelCell = row.getCell(1);
            if (levelCell == null) {
                continue;
            }
            try {
                double levelAsDouble = levelCell.getNumericCellValue();
                if (levelAsDouble < 0) {
                    errors.add(String.format("Invalid river level on row %d.", (r + 1)));
                    continue;
                }

                level.setLevel(new BigDecimal(String.format("%.1f", levelAsDouble)));
            } catch (IllegalStateException | NumberFormatException e) {
                errors.add(String.format("Invalid river level on row %d.", (r + 1)));
                continue;
            }

            if (data.contains(level)) {
                errors.add(String.format("Duplicate date %s on row %d.", formatter.format(level.getMonthDay()),
                        (r + 1)));
            } else {
                data.add(level);
            }
        }

        if (!errors.isEmpty()) {
            throw new RiverLevelReaderException(errors);
        }

        Set<MonthDay> monthDaysInFile = data.stream()
                .map(RiverLevelReference::getMonthDay)
                .collect(Collectors.toSet());

        year.getMonthDays().forEach(d -> {
            if (!monthDaysInFile.contains(d)) {
                RiverLevelReference riverLevel = creator.get();
                riverLevel.setMonthDay(d);
                riverLevel.setLevel(BigDecimal.ZERO);
                data.add(riverLevel);
            }
        });

        return data;
    }
}
