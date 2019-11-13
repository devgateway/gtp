package org.devgateway.toolkit.persistence.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.devgateway.toolkit.persistence.dto.DataDTO;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.persistence.dto.ExcelInfo;
import org.devgateway.toolkit.persistence.excel.service.TranslateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelFileData<T extends DataDTO> implements ExcelFile {

    private final ExcelInfo excelInfo;

    private final List<Object> filters;

    private final String sheetName;

    private final List<T> objects;

    private final Workbook workbook;

    private final TranslateService translateService;

    public ExcelFileData(final List<T> objects, final TranslateService translateService) {
        Validate.notNull(objects, "The list of objects can't be null!");
        Validate.noNullElements(objects, "The list of objects can't contain null elements!");

        this.objects = objects;
        this.translateService = translateService;
        this.excelInfo = null;
        this.filters = null;
        this.sheetName = null;

        // create the excel file
        this.workbook = new SXSSFWorkbook(100);
    }

    public ExcelFileData(ExcelInfo<T> info) {
        Validate.notNull(info.getObjects(), "The list of objects can't be null!");
        Validate.noNullElements(info.getObjects(), "The list of objects can't contain null elements!");

        this.objects = info.getObjects();
        this.translateService = info.getTranslateService();
        this.excelInfo = info;
        this.filters = Arrays.asList(info.getFilters());
        this.sheetName = info.getSheetName();

        // create the excel file
        this.workbook = new SXSSFWorkbook(100);
    }

    @Override
    public Workbook createWorkbook() {

        // don't do anything if the list of objects is empty, just display the error message.
        if (objects.isEmpty()) {
            final ExcelSheet excelSheet = new ExcelSheetDefault(this.workbook, this.translateService, "no data");
            excelSheet.emptySheet();
        } else {
            final Class clazz = this.objects.get(0).getClass();
            String name = StringUtils.isNotBlank(sheetName) ? sheetName : clazz.getSimpleName().toLowerCase();
            final ExcelSheet excelSheet = new ExcelSheetDefault(this.workbook, this.translateService, name);

            if (excelInfo != null) {
                excelSheet.writeIntro(ExcelInfo.class, excelInfo);
            }

            if (filters != null && !filters.isEmpty()) {
                excelSheet.writeSheet(ExcelFilterDTO.class, filters, false);
            }
            excelSheet.writeSheet(clazz, new ArrayList<>(objects), true);
        }

        return workbook;
    }
}