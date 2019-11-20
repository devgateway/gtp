package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

public class AgricultureOrientationIndexDTO extends DataDTO {

    @ExcelExport
    private String indexType;

    @ExcelExport
    private Double budgetedExpenditures;

    @ExcelExport
    private Double disbursedExpenditures;

    @ExcelExport
    private Double subsidies;

    @ExcelExport
    private String variableType;

    public AgricultureOrientationIndexDTO(AgricultureOrientationIndexIndicator aoi) {
        super(aoi);
        this.indexType = getStr(aoi.getIndexType());
        this.budgetedExpenditures = aoi.getBudgetedExpenditures();
        this.disbursedExpenditures = aoi.getDisbursedExpenditures();
        this.subsidies = aoi.getSubsidies();
        this.variableType = aoi.getVariableType();
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public Double getBudgetedExpenditures() {
        return budgetedExpenditures;
    }

    public void setBudgetedExpenditures(Double budgetedExpenditures) {
        this.budgetedExpenditures = budgetedExpenditures;
    }

    public Double getDisbursedExpenditures() {
        return disbursedExpenditures;
    }

    public void setDisbursedExpenditures(Double disbursedExpenditures) {
        this.disbursedExpenditures = disbursedExpenditures;
    }

    public Double getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Double subsidies) {
        this.subsidies = subsidies;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }
}
