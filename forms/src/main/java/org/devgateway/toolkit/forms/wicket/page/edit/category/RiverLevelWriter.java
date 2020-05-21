package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.SortedSet;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelWriter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    public <T extends IRiverLevel> void write(HydrologicalYear year, SortedSet<T> levels, OutputStream outputStream,
            BiFunction<MonthDay, BigDecimal, T> creator) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        SortedSet<T> allLevels = new TreeSet<>(levels);
        year.getMonthDays().forEach(md -> allLevels.add(creator.apply(md, null)));

        XSSFSheet sheet = workbook.createSheet();

        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Jour");
        header.createCell(1).setCellValue(year.toString());

        int i = 1;
        for (T level : allLevels) {
            XSSFRow row = sheet.createRow(i++);

            row.createCell(0).setCellValue(formatter.format(level.getMonthDay()));

            if (level.getLevel() != null) {
                row.createCell(1).setCellValue(level.getLevel().doubleValue());
            }
        }

        workbook.write(outputStream);
    }
}
