package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class DataDTO {

    private static final String EMPTY = "";

    private Long id;

    private String lang;

    @ExcelExport(useTranslation = true)
    private Integer year;

    public DataDTO(Data data, String lang) {
        this.lang = lang;
        this.id = data.getId();
        this.year = data.getYear();
    }

    protected String getStr(Labelable labelable) {
        String ret = EMPTY;
        if (labelable != null) {
            if (lang != null) {
                ret = labelable.getLabel(lang).replace(",", " ");
            } else {
                ret = labelable.getLabel().replace(",", " ");
            }
        }
        return ret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
