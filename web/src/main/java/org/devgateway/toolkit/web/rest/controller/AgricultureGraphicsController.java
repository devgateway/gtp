package org.devgateway.toolkit.web.rest.controller;

import javax.validation.Valid;

import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.service.AgricultureChartsService;
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
@RequestMapping("/api/graphics/agriculture")
public class AgricultureGraphicsController {

    @Autowired
    private AgricultureChartsService agricultureChartsService;

    @GetMapping("all")
    public AgricultureChartsData getAgricultureChartsData() {
        return agricultureChartsService.getAgricultureChartsData();
    }

    @GetMapping("config")
    public AgricultureConfig getCommonConfig() {
        return agricultureChartsService.getAgricultureConfig();
    }

    @GetMapping("product-prices/config")
    public ProductPricesChartConfig getProductPricesChartConfig() {
        return agricultureChartsService.getProductPricesChartConfig();
    }

    @PostMapping("product-prices/data")
    public ProductPricesChartData getProductPricesChartData(
            @RequestBody @Valid ProductPricesChartFilter filter) {
        return agricultureChartsService.getProductPricesChartData(filter);
    }
}
