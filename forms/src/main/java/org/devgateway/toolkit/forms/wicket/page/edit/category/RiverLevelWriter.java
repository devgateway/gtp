package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverLevelReference;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelWriter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

    public void write(HydrologicalYear year, List<RiverLevelReference> levels, OutputStream outputStream)
            throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        List<RiverLevelReference> sortedLevels = new ArrayList<>(levels);
        Collections.sort(sortedLevels);

        XSSFSheet sheet = workbook.createSheet();

        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Jour");
        header.createCell(1).setCellValue(year.toString());

        for (int i = 0; i < sortedLevels.size(); i++) {
            RiverLevelReference level = sortedLevels.get(i);
            XSSFRow row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(formatter.format(level.getMonthDay()));

            if (level.getLevel() != null) {
                row.createCell(1).setCellValue(level.getLevel().doubleValue());
            }
        }

        workbook.write(outputStream);
    }
}
