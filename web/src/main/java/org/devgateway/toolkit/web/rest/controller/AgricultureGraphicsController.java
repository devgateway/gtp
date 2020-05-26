package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Octavian Ciubotaru
 */
@RestController
@RequestMapping("/api/graphics/agriculture")
public class AgricultureGraphicsController {

    @Autowired
    private ChartService chartService;

    @GetMapping("all")
    public AgricultureChartsData getAgricultureChartsData() {
        return chartService.getAgricultureChartsData();
    }

    @GetMapping("config")
    public AgricultureConfig getCommonConfig() {
        return chartService.getAgricultureConfig();
    }
}
