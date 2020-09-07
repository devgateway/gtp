package org.devgateway.toolkit.persistence.service.charts;

import org.devgateway.toolkit.persistence.dto.livestock.LivestockCharts;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityData;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityFilter;

/**
 * @author Nadejda Mandrescu
 */
public interface LivestockChartsService {

    LivestockCharts getLivestockCharts();

    LivestockConfig getLivestockConfig();

    DiseaseQuantityData getDiseaseQuantityData(DiseaseQuantityFilter filter);

    DiseaseQuantityConfig getDiseaseQuantityConfig();
}
