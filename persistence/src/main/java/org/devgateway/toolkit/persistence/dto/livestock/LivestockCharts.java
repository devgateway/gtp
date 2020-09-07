package org.devgateway.toolkit.persistence.dto.livestock;

import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityChart;

/**
 * @author Nadejda Mandrescu
 */
public class LivestockCharts {

    private final CommonConfig commonConfig;

    private final LivestockConfig livestockConfig;

    private final DiseaseQuantityChart diseaseQuantityChart;

    public LivestockCharts(CommonConfig commonConfig, LivestockConfig livestockConfig,
            DiseaseQuantityChart diseaseQuantityChart) {
        this.commonConfig = commonConfig;
        this.livestockConfig = livestockConfig;
        this.diseaseQuantityChart = diseaseQuantityChart;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public LivestockConfig getLivestockConfig() {
        return livestockConfig;
    }

    public DiseaseQuantityChart getDiseaseQuantityChart() {
        return diseaseQuantityChart;
    }
}
