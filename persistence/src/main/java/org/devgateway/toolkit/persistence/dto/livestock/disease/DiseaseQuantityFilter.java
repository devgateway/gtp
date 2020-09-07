package org.devgateway.toolkit.persistence.dto.livestock.disease;

import javax.validation.constraints.NotNull;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityFilter {

    @NotNull
    private final int year;

    @NotNull
    private final Long diseaseId;

    public DiseaseQuantityFilter(@NotNull int year, @NotNull Long diseaseId) {
        this.year = year;
        this.diseaseId = diseaseId;
    }

    public int getYear() {
        return year;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }
}
