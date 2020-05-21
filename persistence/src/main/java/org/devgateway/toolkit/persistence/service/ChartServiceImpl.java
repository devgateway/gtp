package org.devgateway.toolkit.persistence.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.MonthDay;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChart;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonPrediction;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.repository.category.PluviometricPostRepository;
import org.devgateway.toolkit.persistence.repository.indicator.RainSeasonRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainSeasonStartReferenceRepository;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.devgateway.toolkit.persistence.service.indicator.RiverStationYearlyLevelsService;
import org.devgateway.toolkit.persistence.service.reference.RainLevelReferenceService;
import org.devgateway.toolkit.persistence.service.reference.RiverStationYearlyLevelsReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private DecadalRainfallService decadalRainfallService;

    @Autowired
    private RainLevelReferenceService rainLevelReferenceService;

    @Autowired
    private RainSeasonRepository rainSeasonRepository;

    @Autowired
    private RainSeasonStartReferenceRepository rainSeasonStartReferenceRepository;

    @Autowired
    private PluviometricPostRepository pluviometricPostRepository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private RiverStationYearlyLevelsService riverStationYearlyLevelsService;

    @Autowired
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    @Override
    @Transactional(readOnly = true)
    public ChartsData getCharts() {
        RainLevelChartConfig rainLevelChartConfig = getRainLevelConfig();
        return new ChartsData(
                getCommonConfig(),
                getRainLevelChart(rainLevelChartConfig),
                getDrySequenceChart(rainLevelChartConfig),
                getSeasonChart(),
                getRiverLevelChart());
    }

    @Override
    @Transactional(readOnly = true)
    public CommonConfig getCommonConfig() {
        List<PluviometricPost> posts = pluviometricPostRepository.findAll();
        List<Department> departments = posts.stream().map(PluviometricPost::getDepartment).distinct().collect(toList());
        List<Region> regions = departments.stream().map(Department::getRegion).distinct().collect(toList());
        List<Zone> zones = regions.stream().map(Region::getZone).distinct().collect(toList());
        return new CommonConfig(posts, departments, regions, zones);
    }

    private RainLevelChart getRainLevelChart(RainLevelChartConfig config) {
        RainLevelChartFilter filter = getRainLevelFilter(config);

        RainLevelChartData data = null;
        if (filter.getPluviometricPostId() != null && !filter.getYears().isEmpty()) {
            data = getRainLevelData(filter);
        }

        return new RainLevelChart(config, filter, data);
    }

    private RainLevelChartFilter getRainLevelFilter(RainLevelChartConfig config) {
        List<Integer> years = config.getYears().stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .collect(toList());

        List<Long> posts = config.getPluviometricPostIds();
        Long postId = posts.isEmpty() ? null : posts.get(0);

        return new RainLevelChartFilter(years, postId);
    }

    @Override
    @Transactional(readOnly = true)
    public RainLevelChartConfig getRainLevelConfig() {
        return new RainLevelChartConfig(
                decadalRainfallService.findYearsWithData(),
                decadalRainfallService.findPluviometricPostsWithData());
    }

    @Override
    @Transactional(readOnly = true)
    public RainLevelChartData getRainLevelData(RainLevelChartFilter filter) {
        return new RainLevelChartData(
                decadalRainfallService.findRainLevels(filter.getYears(), filter.getPluviometricPostId()),
                rainLevelReferenceService.findReferenceLevels(filter.getYears(), filter.getPluviometricPostId()));
    }

    public DrySequenceChart getDrySequenceChart(RainLevelChartConfig config) {
        DrySequenceChartFilter filter = getDrySequenceChartFilter(config);

        DrySequenceChartData data;
        if (filter.getYear() != null && filter.getPluviometricPostId() != null) {
            data = getDrySequenceData(filter);
        } else {
            data = null;
        }

        return new DrySequenceChart(filter, data);
    }

    private DrySequenceChartFilter getDrySequenceChartFilter(RainLevelChartConfig config) {
        Integer year = config.getYears().isEmpty() ? null : config.getYears().last();
        Long postId = config.getPluviometricPostIds().isEmpty() ? null : config.getPluviometricPostIds().get(0);
        return new DrySequenceChartFilter(year, postId);
    }

    @Override
    public DrySequenceChartData getDrySequenceData(DrySequenceChartFilter filter) {
        return new DrySequenceChartData(
                decadalRainfallService.findMonthDecadalDaysWithRain(filter.getYear(), filter.getPluviometricPostId()));
    }

    private SeasonChart getSeasonChart() {
        SeasonChartConfig config = getRainSeasonConfig();
        SeasonChartFilter filter;
        SeasonChartData data;
        if (!config.getYears().isEmpty()) {
            filter = new SeasonChartFilter(config.getYears().last());
            data = getRainSeasonData(filter);
        } else {
            filter = null;
            data = null;
        }
        return new SeasonChart(config, filter, data);
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonChartConfig getRainSeasonConfig() {
        return new SeasonChartConfig(rainSeasonRepository.findYearsWithData());
    }

    @Override
    @Transactional(readOnly = true)
    public SeasonChartData getRainSeasonData(SeasonChartFilter filter) {
        Integer year = filter.getYear();

        List<PluviometricPostRainSeason> postRainSeasons = rainSeasonRepository.findByYear(year)
                .map(RainSeason::getPostRainSeasons)
                .orElse(emptyList());

        RainSeasonStartReference seasonRef = rainSeasonStartReferenceRepository
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(year, year);

        Map<PluviometricPost, MonthDay> referenceStartByPost = seasonRef.getPostReferences().stream()
                .filter(p -> p.getStartReference() != null)
                .collect(toMap(
                        RainSeasonPluviometricPostReferenceStart::getPluviometricPost,
                        RainSeasonPluviometricPostReferenceStart::getStartReference));

        List<SeasonPrediction> predictions = postRainSeasons.stream()
                .filter(ps -> ps.isPublished() && referenceStartByPost.containsKey(ps.getPluviometricPost()))
                .map(ps -> newSeasonStart(ps, referenceStartByPost.get(ps.getPluviometricPost())))
                .collect(toList());

        Integer referenceYearStart = seasonRef.getReferenceYearStart();
        Integer referenceYearEnd = seasonRef.getReferenceYearEnd();

        return new SeasonChartData(referenceYearStart, referenceYearEnd, predictions);
    }

    private SeasonPrediction newSeasonStart(PluviometricPostRainSeason ps, MonthDay plannedMonthDay) {
        return new SeasonPrediction(ps.getPluviometricPost().getId(), plannedMonthDay, ps.getStartDate());
    }

    private RiverLevelChart getRiverLevelChart() {
        RiverLevelChartConfig config = getRiverLevelConfig();
        RiverLevelChartFilter filter = getRiverLevelFilter();
        RiverLevelChartData data = getRiverLevelData(filter);

        return new RiverLevelChart(config, filter, data);
    }

    private RiverLevelChartFilter getRiverLevelFilter() {
        Set<HydrologicalYear> years = ImmutableSet.of(HydrologicalYear.now());

        Long riverStationId = adminSettingsService.get().getDefaultRiverStation().getId();

        return new RiverLevelChartFilter(years, riverStationId);
    }

    @Override
    @Transactional
    public RiverLevelChartConfig getRiverLevelConfig() {
        SortedSet<HydrologicalYear> years = new TreeSet<>(riverStationYearlyLevelsService.findYearsWithLevels());
        List<RiverStation> riverStations = riverStationYearlyLevelsService.findStationsWithLevels();
        return new RiverLevelChartConfig(years, riverStations);
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
