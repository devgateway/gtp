package org.devgateway.toolkit.persistence.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.CommonConfig;
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
import org.devgateway.toolkit.persistence.service.reference.RainLevelReferenceService;
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

    @Override
    @Transactional(readOnly = true)
    public ChartsData getCharts() {
        return new ChartsData(
                getCommonConfig(),
                getRainLevelChart(),
                getSeasonChart());
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

    private RainLevelChart getRainLevelChart() {
        RainLevelChartConfig config = getRainLevelConfig();
        RainLevelChartFilter filter = getRainLevelFilter(config);

        RainLevelChartData data = null;
        if (filter.getPluviometricPostId() != null && !filter.getYears().isEmpty()) {
            data = getRainLevelData(filter);
        }

        return new RainLevelChart(config, filter, data);
    }

    private RainLevelChartFilter getRainLevelFilter(RainLevelChartConfig config) {
        List<Integer> years = new ArrayList<>();
        if (!config.getYears().isEmpty()) {
            years.add(config.getYears().last());
        }

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

        Map<PluviometricPost, MonthDay> referenceStartByPost = getReferenceStartByPost(seasonRef);

        List<SeasonPrediction> predictions = postRainSeasons.stream()
                .filter(AbstractStatusAuditableEntity::isPublished)
                .map(ps -> newSeasonStart(ps, referenceStartByPost.get(ps.getPluviometricPost())))
                .collect(toList());

        Integer referenceYearStart = seasonRef != null ? seasonRef.getReferenceYearStart() : null;
        Integer referenceYearEnd = seasonRef != null ? seasonRef.getReferenceYearEnd() : null;

        return new SeasonChartData(referenceYearStart, referenceYearEnd, predictions);
    }

    private Map<PluviometricPost, MonthDay> getReferenceStartByPost(RainSeasonStartReference seasonRef) {
        Map<PluviometricPost, MonthDay> referenceStartByPost = new HashMap<>();
        if (seasonRef != null) {
            seasonRef.getPostReferences()
                    .forEach(p -> referenceStartByPost.put(p.getPluviometricPost(), p.getStartReference()));
        }
        return referenceStartByPost;
    }

    private SeasonPrediction newSeasonStart(PluviometricPostRainSeason ps, MonthDay plannedMonthDay) {
        return new SeasonPrediction(ps.getPluviometricPost().getId(), plannedMonthDay, ps.getStartDate());
    }
}