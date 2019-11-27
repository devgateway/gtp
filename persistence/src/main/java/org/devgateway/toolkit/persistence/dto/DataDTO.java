package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class DataDTO {

    private static final String EMPTY = "";

    private Long id;

    @ExcelExport
    private Integer year;

    public DataDTO(Data data) {
        this.id = data.getId();
        this.year = data.getYear();
    }

    protected String getStr(Labelable labelable) {
        String ret = EMPTY;
        if (labelable != null) {
            ret = labelable.getLabel().replace(",", " ");
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
}
