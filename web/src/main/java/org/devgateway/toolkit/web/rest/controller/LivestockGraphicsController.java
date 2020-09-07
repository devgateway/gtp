package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dto.livestock.LivestockCharts;
import org.devgateway.toolkit.persistence.dto.livestock.LivestockConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityConfig;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityData;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityFilter;
import org.devgateway.toolkit.persistence.service.charts.LivestockChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Nadejda Mandrescu
 */
@RestController
@RequestMapping("/api/graphics/livestock")
public class LivestockGraphicsController {

    @Autowired
    private LivestockChartsService livestockChartsService;

    @GetMapping("all")
    public LivestockCharts getLivestockCharts() {
        return livestockChartsService.getLivestockCharts();
    }

    @GetMapping("config")
    public LivestockConfig getLivestockConfig() {
        return livestockChartsService.getLivestockConfig();
    }

    @GetMapping("disease-quantity/config")
    public DiseaseQuantityConfig getDiseaseQuantityConfig() {
        return livestockChartsService.getDiseaseQuantityConfig();
    }

    @PostMapping("disease-quantity/data")
    public DiseaseQuantityData getDiseaseQuantityData(@RequestBody @Valid DiseaseQuantityFilter filter) {
        return livestockChartsService.getDiseaseQuantityData(filter);
    }

}
