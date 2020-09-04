package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.service.charts.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Octavian Ciubotaru
 */
@RestController
public class CommonConfigController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/api/config")
    public CommonConfig getCommonConfig() {
        return chartService.getCommonConfig();
    }
}
