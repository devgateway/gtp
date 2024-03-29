package org.devgateway.toolkit.persistence.service.charts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.WaterConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.ChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMap;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapConfig;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChart;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonPrediction;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.indicator.RainSeasonService;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;
import org.devgateway.toolkit.persistence.service.indicator.rainfallMap.DecadalRainfallMapService;
import org.devgateway.toolkit.persistence.service.indicator.river.RiverStationYearlyLevelsService;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;
import org.devgateway.toolkit.persistence.service.reference.RiverStationYearlyLevelsReferenceService;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class WaterChartsServiceImpl implements WaterChartsService {

    @Autowired
    private YearlyRainfallService yearlyRainfallService;

    @Autowired
    private RainLevelReferenceService rainLevelReferenceService;

    @Autowired
    private DecadalRainfallMapService decadalRainfallMapService;

    @Autowired
    private RainSeasonService rainSeasonService;

    @Autowired
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    @Autowired
    private PluviometricPostService pluviometricPostService;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private RiverStationYearlyLevelsService riverStationYearlyLevelsService;

    @Autowired
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    @Autowired
    private ChartService chartService;

    @Autowired
    private IndicatorMetadataService indicatorMetadataService;

    @Override
    @Transactional(readOnly = true)
    public ChartsData getCharts() {
        RainLevelChartConfig rainLevelChartConfig = getRainLevelConfig();
        WaterConfig waterConfig = getWaterConfig();
        return new ChartsData(
                chartService.getCommonConfig(),
                waterConfig,
                getRainLevelChart(rainLevelChartConfig, waterConfig),
                getRainMap(),
                getDrySequenceChart(rainLevelChartConfig, waterConfig),
                getSeasonChart(),
                getRiverLevelChart());
    }

    @Override
    @Transactional(readOnly = true)
    public WaterConfig getWaterConfig() {
        List<PluviometricPost> posts = pluviometricPostService.findAll();
        return new WaterConfig(posts);
    }

    private RainLevelChart getRainLevelChart(RainLevelChartConfig config, WaterConfig waterConfig) {
        RainLevelChartFilter filter = getRainLevelFilter(config, waterConfig);

        RainLevelChartData data;
        if (filter.getPluviometricPostId() != null) {
            data = getRainLevelData(filter);
        } else {
            data = new RainLevelChartData(ImmutableList.of(), ImmutableList.of());
        }

        return new RainLevelChart(config, filter, data);
    }

    private RainLevelChartFilter getRainLevelFilter(RainLevelChartConfig config, WaterConfig waterConfig) {
        List<Integer> years;
        if (config.getYears().isEmpty()) {
            years = ImmutableList.of(Year.now(AD3Clock.systemDefaultZone()).getValue());
        } else {
            years = config.getYears().stream()
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .collect(toList());
        }

        List<Long> posts = config.getPluviometricPostIds();
        Long postId = getFirstOr(posts, () -> getFirstPost(waterConfig));

        return new RainLevelChartFilter(years, postId);
    }

    private Long getFirstPost(WaterConfig waterConfig) {
        return waterConfig.getPosts().isEmpty() ? null : waterConfig.getPosts().get(0).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public RainLevelChartConfig getRainLevelConfig() {
        return new RainLevelChartConfig(
                getOrgNameForIndicatorType(IndicatorType.RAINFALL),
                yearlyRainfallService.findYearsWithData(),
                yearlyRainfallService.findPluviometricPostsWithData());
    }

    private String getOrgNameForIndicatorType(IndicatorType indicatorType) {
        return indicatorMetadataService.findByType(indicatorType).getOrganization().getLabel();
    }

    @Override
    @Transactional(readOnly = true)
    public RainLevelChartData getRainLevelData(RainLevelChartFilter filter) {
        return new RainLevelChartData(
                yearlyRainfallService.findRainLevels(filter.getYears(), filter.getPluviometricPostId()),
                rainLevelReferenceService.findReferenceLevels(filter.getYears(), filter.getPluviometricPostId()));
    }

    private RainMap getRainMap() {
        RainMapConfig config = getRainMapConfig();
        return new RainMap(config, getRainMapFilter(config));
    }

    @Override
    @Transactional(readOnly = true)
    public RainMapConfig getRainMapConfig() {
        return new RainMapConfig(
                getOrgNameForIndicatorType(IndicatorType.RAINFALL_MAP),
                new TreeSet<>(decadalRainfallMapService.findYearsWithData()));
    }

    public RainMapFilter getRainMapFilter(RainMapConfig config) {
        Integer year = config.getYears().isEmpty() ? null : config.getYears().last();
        Month month = year == null ? null : decadalRainfallMapService.findLastMonthWithData(year);
        Decadal decadal = month == null ? null : decadalRainfallMapService.findLastDecadalWithData(year, month);
        return new RainMapFilter(year, month, decadal, null);

    }

    @Override
    public DecadalRainfallMap getRainMapData(RainMapFilter filter) {
        return decadalRainfallMapService.findByYearAndMonthAndDecadal(
                filter.getYear(), filter.getMonth(), filter.getDecadal());
    }

    public DrySequenceChart getDrySequenceChart(RainLevelChartConfig config, WaterConfig waterConfig) {
        DrySequenceChartFilter filter = getDrySequenceChartFilter(config, waterConfig);

        DrySequenceChartData data;
        if (filter.getPluviometricPostId() != null) {
            data = getDrySequenceData(filter);
        } else {
            data = new DrySequenceChartData(ImmutableList.of());
        }

        ChartConfig chartConfig = getDrySequenceConfig();

        return new DrySequenceChart(chartConfig, filter, data);
    }

    @Override
    public ChartConfig getDrySequenceConfig() {
        return new ChartConfig(getOrgNameForIndicatorType(IndicatorType.RAINFALL_SEASON));
    }

    private DrySequenceChartFilter getDrySequenceChartFilter(RainLevelChartConfig config, WaterConfig waterConfig) {
        Integer year = config.getYears().isEmpty()
                ? Year.now(AD3Clock.systemDefaultZone()).getValue()
                : config.getYears().last();
        Long postId = getFirstOr(config.getPluviometricPostIds(), () -> getFirstPost(waterConfig));
        return new DrySequenceChartFilter(year, postId);
    }

    private <T> T getFirstOr(List<T> list, Supplier<T> supplier) {
        return list.isEmpty() ? supplier.get() : list.get(0);
    }

    @Override
    public DrySequenceChartData getDrySequenceData(DrySequenceChartFilter filter) {
        return new DrySequenceChartData(
                yearlyRainfallService.findMonthDecadalDaysWithRain(filter.getYear(), filter.getPluviometricPostId()));
    }

    private SeasonChart getSeasonChart() {
        SeasonChartConfig config = getRainSeasonConfig();

        Integer year = config.getYears().isEmpty()
                ? Year.now(AD3Clock.systemDefaultZone()).getValue()
                : config.getYears().last();
        SeasonChartFilter filter = new SeasonChartFilter(year);

        return new SeasonChart(config, filter, getRainSeasonData(filter));
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonChartConfig getRainSeasonConfig() {
        return new SeasonChartConfig(
                getOrgNameForIndicatorType(IndicatorType.RAINFALL_SEASON),
                rainSeasonService.findYearsWithData());
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonChartData getRainSeasonData(SeasonChartFilter filter) {
        Integer year = filter.getYear();

        List<PluviometricPostRainSeason> postRainSeasons = rainSeasonService.findByYear(year);

        RainSeasonStartReference seasonRef = rainSeasonStartReferenceService
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(year);

        Map<PluviometricPost, MonthDay> referenceStartByPost = seasonRef.getPostReferences().stream()
                .filter(p -> p.getStartReference() != null)
                .collect(toMap(
                        RainSeasonPluviometricPostReferenceStart::getPluviometricPost,
                        RainSeasonPluviometricPostReferenceStart::getStartReference));

        List<SeasonPrediction> predictions = postRainSeasons.stream()
                .filter(ps -> ps.isPublished() && referenceStartByPost.containsKey(ps.getPluviometricPost()))
                .map(ps -> newSeasonStart(ps, referenceStartByPost.get(ps.getPluviometricPost())))
                .collect(toList());

        Integer yearStart = seasonRef.getYearStart();
        Integer yearEnd = seasonRef.getYearEnd();
        Integer referenceYearStart = seasonRef.getReferenceYearStart();
        Integer referenceYearEnd = seasonRef.getReferenceYearEnd();

        return new SeasonChartData(yearStart, yearEnd, referenceYearStart, referenceYearEnd, predictions);
    }

    private SeasonPrediction newSeasonStart(PluviometricPostRainSeason ps, MonthDay plannedMonthDay) {
        return new SeasonPrediction(ps.getPluviometricPost().getId(), plannedMonthDay, ps.getStartDate());
    }

    private RiverLevelChart getRiverLevelChart() {
        RiverLevelChartConfig config = getRiverLevelConfig();
        RiverLevelChartFilter filter = getRiverLevelFilter(config);
        RiverLevelChartData data;
        if (filter.getRiverStationId() == null) {
            data = new RiverLevelChartData(ImmutableList.of(), ImmutableList.of());
        } else {
            data = getRiverLevelData(filter);
        }
        return new RiverLevelChart(config, filter, data);
    }

    private RiverLevelChartFilter getRiverLevelFilter(RiverLevelChartConfig config) {
        Set<HydrologicalYear> years = config.getYears().isEmpty()
                ? ImmutableSet.of(HydrologicalYear.now())
                : ImmutableSet.of(config.getYears().last());

        Long riverStationId;

        Long defaultRiverStationId = adminSettingsService.get().getDefaultRiverStation().getId();

        if (riverStationYearlyLevelsService.hasLevels(years, defaultRiverStationId)) {
            riverStationId = defaultRiverStationId;
        } else {
            List<RiverStation> stationsWithLevels = riverStationYearlyLevelsService.findStationsWithLevels(years);
            if (!stationsWithLevels.isEmpty()) {
                riverStationId = stationsWithLevels.get(0).getId();
            } else {
                riverStationId = defaultRiverStationId;
            }
        }

        return new RiverLevelChartFilter(years, riverStationId);
    }

    @Override
    @Transactional
    public RiverLevelChartConfig getRiverLevelConfig() {
        SortedSet<HydrologicalYear> years = new TreeSet<>(riverStationYearlyLevelsService.findYearsWithLevels());
        List<RiverStation> riverStations = riverStationYearlyLevelsService.findStationsWithLevels();
        String org = getOrgNameForIndicatorType(IndicatorType.RIVER_LEVEL);
        return new RiverLevelChartConfig(org, years, riverStations);
    }

    @Override
    @Transactional
    public RiverLevelChartData getRiverLevelData(RiverLevelChartFilter filter) {
        List<RiverStationYearlyLevels> yearlyLevels = riverStationYearlyLevelsService
                .findByYearInAndStationId(filter.getYears(), filter.getRiverStationId()).stream()
                .filter(yl -> !yl.getLevels().isEmpty())
                .collect(toList());

        List<RiverStationYearlyLevelsReference> referenceYearlyLevels = riverStationYearlyLevelsReferenceService
                .findByStationId(filter.getRiverStationId()).stream()
                .filter(yl -> !yl.getLevels().isEmpty())
                .collect(toList());

        return new RiverLevelChartData(yearlyLevels, referenceYearlyLevels);
    }
}
