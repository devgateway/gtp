package org.devgateway.toolkit.persistence.dto.ipar;

import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.devgateway.toolkit.persistence.excel.service.TranslateService;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class ExcelInfo<T extends DataDTO> {

    private String sheetName;

    @ExcelExport(name = "Intro")
    private String intro;

    private ExcelFilterDTO filters;

    private List<T> objects;

    private TranslateService translateService;

    public ExcelInfo(String sheetName, String intro, ExcelFilterDTO filters,
                     List<T> objects, TranslateService translateService) {
        this.sheetName = sheetName;
        this.intro = intro;
        this.filters = filters;
        this.objects = objects;
        this.translateService = translateService;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ExcelFilterDTO getFilters() {
        return filters;
    }

    public void setFilters(ExcelFilterDTO filters) {
        this.filters = filters;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public TranslateService getTranslateService() {
        return translateService;
    }

    public void setTranslateService(TranslateService translateService) {
        this.translateService = translateService;
    }
}
