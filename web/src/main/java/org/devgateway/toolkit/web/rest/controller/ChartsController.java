package org.devgateway.toolkit.web.rest.controller;

import javax.validation.Valid;

import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.service.ChartService;
import org.devgateway.toolkit.persistence.service.TestDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Octavian Ciubotaru
 */
@RestController
public class ChartsController {

    @Autowired
    private TestDataGenerator testDataGenerator;

    @Autowired
    private ChartService chartService;

    @GetMapping("/charts/all")
    public ChartsData getCharts() {
        return chartService.getCharts();
    }

    @GetMapping("/charts/rain-level/config")
    public RainLevelChartConfig getConfig() {
        return chartService.getRainLevelConfig();
    }

    @PostMapping("/charts/rain-level/data")
    public RainLevelChartData getChartData(@RequestBody @Valid RainLevelChartFilter filter) {
        return chartService.getRainLevelData(filter);
    }

    @GetMapping("/charts/rain-level/gen-test-data")
    public void generateTestData() {
//        testDataGenerator.generate();
    }

    @GetMapping("/charts/rain-level/gen-test-refs")
    public void generateTestRefs() {
//        testDataGenerator.generateRefs();
    }
}
