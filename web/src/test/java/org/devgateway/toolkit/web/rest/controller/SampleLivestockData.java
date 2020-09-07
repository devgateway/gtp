package org.devgateway.toolkit.web.rest.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockCharts;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityChart;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityData;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityFilter;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class SampleLivestockData {

    private final LivestockCharts livestockCharts;

    private final LivestockDisease distomatose;

    public SampleLivestockData(SampleCommonData commonData) {
        distomatose = new LivestockDisease(1L, "Distomatose", "Distomatose");
        List<LivestockDisease> diseases = ImmutableList.of(distomatose);

        LivestockConfig livestockConfig = new LivestockConfig(diseases);


        livestockCharts = new LivestockCharts(commonData.getCommonConfig(), livestockConfig,
                getDiseaseQuantityChart(commonData.getRegionKedougou()));
    }

    private DiseaseQuantityChart getDiseaseQuantityChart(Region region) {
        DiseaseQuantityConfig config = new DiseaseQuantityConfig(ImmutableSortedSet.of(2018, 2019, 2020));
        DiseaseQuantityFilter filter = new DiseaseQuantityFilter(2020, distomatose.getId());
        DiseaseQuantityData data = new DiseaseQuantityData(ImmutableList.of(
                new DiseaseQuantity(region, distomatose, Month.JANUARY, 10L),
                new DiseaseQuantity(region, distomatose, Month.FEBRUARY, 20L),
                new DiseaseQuantity(region, distomatose, Month.DECEMBER, 5L)
        ));

        return new DiseaseQuantityChart(config, filter, data);
    }

    public LivestockCharts getLivestockCharts() {
        return livestockCharts;
    }
}
