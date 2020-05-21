package org.devgateway.toolkit.web.rest.controller;

import javax.validation.Valid;

import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Octavian Ciubotaru
 */
@RestController
@RequestMapping("/api/graphics/water")
public class WaterGraphicsController {

    @Autowired
    private ChartService chartService;

    @GetMapping("all")
    public ChartsData getCharts() {
        return chartService.getCharts();
    }

    @GetMapping("common-config")
    public CommonConfig getCommonConfig() {
        return chartService.getCommonConfig();
    }

    @GetMapping("rain-level/config")
    public RainLevelChartConfig getRainLevelConfig() {
        return chartService.getRainLevelConfig();
    }

    @PostMapping("rain-level/data")
    public RainLevelChartData getRainLevelChartData(@RequestBody @Valid RainLevelChartFilter filter) {
        return chartService.getRainLevelData(filter);
    }

    @PostMapping("dry-sequence/data")
    public DrySequenceChartData getDrySequenceChartData(@RequestBody @Valid DrySequenceChartFilter filter) {
        return chartService.getDrySequenceData(filter);
    }

    @GetMapping("rain-season/config")
    public SeasonChartConfig getRainSeasonConfig() {
        return chartService.getRainSeasonConfig();
    }

    @PostMapping("rain-season/data")
    public SeasonChartData getRainSeasonData(@RequestBody @Valid SeasonChartFilter filter) {
        return chartService.getRainSeasonData(filter);
    }

    @GetMapping("river-level/config")
    public RiverLevelChartConfig getRiverLevelConfig() {
        return chartService.getRiverLevelConfig();
    }

    @PostMapping("river-level/data")
    public RiverLevelChartData getRiverLevelData(@RequestBody @Valid RiverLevelChartFilter filter) {
        return chartService.getRiverLevelData(filter);
    }
}
