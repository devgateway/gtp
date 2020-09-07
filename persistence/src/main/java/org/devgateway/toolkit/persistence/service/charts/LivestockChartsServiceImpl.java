package org.devgateway.toolkit.persistence.service.charts;

import static java.util.stream.Collectors.toCollection;

import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockCharts;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityChart;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityData;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityFilter;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.LivestockDiseaseService;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class LivestockChartsServiceImpl implements LivestockChartsService {
    @Autowired
    private LivestockDiseaseService livestockDiseaseService;

    @Autowired
    private DiseaseYearlySituationService diseaseYearlySituationService;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private ChartService chartService;

    @Override
    public LivestockCharts getLivestockCharts() {
        CommonConfig commonConfig = chartService.getCommonConfig();
        LivestockConfig livestockConfig = getLivestockConfig();

        DiseaseQuantityChart diseaseQuantityChart = getDiseaseQuantityChart(livestockConfig);

        return new LivestockCharts(commonConfig, livestockConfig, diseaseQuantityChart);
    }

    @Override
    public LivestockConfig getLivestockConfig() {
        return new LivestockConfig(livestockDiseaseService.findAll(Sort.by(Sort.Direction.ASC, "label")));
    }

    private DiseaseQuantityChart getDiseaseQuantityChart(LivestockConfig livestockConfig) {
        DiseaseQuantityConfig config = getDiseaseQuantityConfig();
        DiseaseQuantityFilter filter = getDefaultDiseaseQuantityFilter(livestockConfig, config);
        DiseaseQuantityData data = getDiseaseQuantityData(filter);
        return new DiseaseQuantityChart(config, filter, data);
    }

    @Override
    public DiseaseQuantityData getDiseaseQuantityData(DiseaseQuantityFilter filter) {
        return new DiseaseQuantityData(diseaseYearlySituationService.findQuantities(
                filter.getYear(), filter.getDiseaseId()));
    }

    @Override
    public DiseaseQuantityConfig getDiseaseQuantityConfig() {
        Integer sinceYear = adminSettingsService.getStartingYear();
        SortedSet<Integer> years = diseaseYearlySituationService.findYearsWithQuantities().stream()
                .filter(year -> year >= sinceYear).sorted().collect(toCollection(TreeSet::new));
        return new DiseaseQuantityConfig(years);
    }

    private DiseaseQuantityFilter getDefaultDiseaseQuantityFilter(LivestockConfig livestockConfig,
            DiseaseQuantityConfig diseaseQuantityConfig) {
        YearMonth lastYearMonth = getLastYearMonthWithQuantities(diseaseQuantityConfig);

        Long diseaseId = diseaseYearlySituationService.getDiseaseIdWithMaximumQuantity(lastYearMonth);
        if (diseaseId == null && !livestockConfig.getDiseases().isEmpty()) {
            diseaseId = livestockConfig.getDiseases().get(0).getId();
        }

        return new DiseaseQuantityFilter(lastYearMonth.getYear(), diseaseId);
    }

    private YearMonth getLastYearMonthWithQuantities(DiseaseQuantityConfig diseaseQuantityConfig) {
        if (diseaseQuantityConfig.getYears().isEmpty()) {
            return YearMonth.now(AD3Clock.systemDefaultZone());
        }
        Integer year = diseaseQuantityConfig.getYears().last();
        Month month = diseaseYearlySituationService.findLastMonthWithQuantities(year);
        return YearMonth.of(year, month);
    }
}
