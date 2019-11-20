package org.devgateway.toolkit.persistence.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.persistence.dto.ExcelInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelFileData implements ExcelFile {

    private final List<ExcelInfo> infoList;

    private final Workbook workbook;

    public ExcelFileData(List<ExcelInfo> infoList) {
        Validate.notNull(infoList, "The list of objects can't be null!");
        this.infoList = infoList;

        // create the excel file
        this.workbook = new SXSSFWorkbook(100);
    }

    @Override
    public Workbook createWorkbook() {

        for (ExcelInfo info:infoList) {
            // don't do anything if the list of objects is empty, just display the error message.
            if (info.getObjects().isEmpty()) {
                final ExcelSheet excelSheet = new ExcelSheetDefault(this.workbook,
                        info.getTranslateService(), "no data");
                excelSheet.emptySheet();
            } else {
                final Class clazz = info.getObjects().get(0).getClass();
                String name = StringUtils.isNotBlank(info.getSheetName()) ? info.getSheetName()
                        : clazz.getSimpleName().toLowerCase();
                final ExcelSheet excelSheet = new ExcelSheetDefault(this.workbook, info.getTranslateService(), name);

                if (StringUtils.isNotEmpty(info.getIntro())) {
                    excelSheet.writeIntro(info.getIntro());
                }

                if (info.getFilters() != null) {
                    excelSheet.writeSheet(ExcelFilterDTO.class, Arrays.asList(info.getFilters()), false);
                }
                excelSheet.writeSheet(clazz, new ArrayList<>(info.getObjects()), true);
            }
        }

        return workbook;
    }
}