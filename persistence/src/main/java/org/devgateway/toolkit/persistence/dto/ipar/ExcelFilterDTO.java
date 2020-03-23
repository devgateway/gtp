package org.devgateway.toolkit.persistence.dto.ipar;

import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public abstract class ExcelFilterDTO {

    @ExcelExport(name = "Filters")
    protected String filters;

    public abstract String getFilters();

    public abstract void setFilters(String filters);

}
