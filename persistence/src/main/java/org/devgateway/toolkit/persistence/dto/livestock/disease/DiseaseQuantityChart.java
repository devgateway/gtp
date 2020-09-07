package org.devgateway.toolkit.persistence.dto.livestock.disease;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityChart {

    private final DiseaseQuantityConfig config;

    private final DiseaseQuantityFilter filter;

    private final DiseaseQuantityData data;

    public DiseaseQuantityChart(DiseaseQuantityConfig config, DiseaseQuantityFilter filter, DiseaseQuantityData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public DiseaseQuantityConfig getConfig() {
        return config;
    }

    public DiseaseQuantityFilter getFilter() {
        return filter;
    }

    public DiseaseQuantityData getData() {
        return data;
    }
}
