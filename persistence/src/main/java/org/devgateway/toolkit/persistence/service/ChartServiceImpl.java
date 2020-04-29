package org.devgateway.toolkit.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.devgateway.toolkit.persistence.service.reference.RainLevelReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private DecadalRainfallService decadalRainfallService;

    @Autowired
    private RainLevelReferenceService rainLevelReferenceService;

    @Override
    public ChartsData getCharts() {
        return new ChartsData(getRainLevelChart());
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

        List<PluviometricPost> posts = config.getPluviometricPosts();
        Long postId = posts.isEmpty() ? null : posts.get(0).getId();

        return new RainLevelChartFilter(years, postId);
    }

    @Override
    public RainLevelChartConfig getRainLevelConfig() {
        return new RainLevelChartConfig(
                decadalRainfallService.findYearsWithData(),
                decadalRainfallService.findPluviometricPostsWithData());
    }

    @Override
    public RainLevelChartData getRainLevelData(RainLevelChartFilter filter) {
        return new RainLevelChartData(
                decadalRainfallService.findRainLevels(filter.getYears(), filter.getPluviometricPostId()),
                rainLevelReferenceService.findReferenceLevels(filter.getYears(), filter.getPluviometricPostId()));
    }
}
