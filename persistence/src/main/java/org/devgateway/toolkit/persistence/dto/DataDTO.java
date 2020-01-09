package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;

public class DataDTO {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String lang;

    @ExcelExport(useTranslation = true)
    private Integer year;

    public DataDTO(Data data, String lang) {
        this.lang = lang;
        this.id = data.getId();
        this.year = data.getYear();
    }

    protected String getStr(Labelable labelable) {
        String ret = EMPTY_STRING;
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
