package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayer;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.WaterConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapConfig;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.service.charts.WaterChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Octavian Ciubotaru
 */
@RestController
@RequestMapping("/api/graphics/water")
public class WaterGraphicsController {

    @Autowired
    private WaterChartsService waterChartsService;

    @GetMapping("all")
    public ChartsData getCharts() {
        return waterChartsService.getCharts();
    }

    @GetMapping("config")
    public WaterConfig getWaterConfig() {
        return waterChartsService.getWaterConfig();
    }

    @GetMapping("rain-level/config")
    public RainLevelChartConfig getRainLevelConfig() {
        return waterChartsService.getRainLevelConfig();
    }

    @PostMapping("rain-level/data")
    public RainLevelChartData getRainLevelChartData(@RequestBody @Valid RainLevelChartFilter filter) {
        return waterChartsService.getRainLevelData(filter);
    }

    @GetMapping("rain-map/config")
    public RainMapConfig getRainMapConfig() {
        return waterChartsService.getRainMapConfig();
    }

    @PostMapping("rain-map/data")
    public ResponseEntity<Resource> getRainMapData(@RequestBody @Valid RainMapFilter filter) {
        DecadalRainfallMap drm = waterChartsService.getRainMapData(filter);
        RainfallMapLayer layer = drm.getLayerByType(filter.getLayerType());
        FileMetadata layerMetadata = layer == null ? null : layer.getFileSingle();
        return FileMetadataController.responseForFileContentOrNotFound(layerMetadata, null);
    }

    @PostMapping("dry-sequence/data")
    public DrySequenceChartData getDrySequenceChartData(@RequestBody @Valid DrySequenceChartFilter filter) {
        return waterChartsService.getDrySequenceData(filter);
    }

    @GetMapping("rain-season/config")
    public SeasonChartConfig getRainSeasonConfig() {
        return waterChartsService.getRainSeasonConfig();
    }

    @PostMapping("rain-season/data")
    public SeasonChartData getRainSeasonData(@RequestBody @Valid SeasonChartFilter filter) {
        return waterChartsService.getRainSeasonData(filter);
    }

    @GetMapping("river-level/config")
    public RiverLevelChartConfig getRiverLevelConfig() {
        return waterChartsService.getRiverLevelConfig();
    }

    @PostMapping("river-level/data")
    public RiverLevelChartData getRiverLevelData(@RequestBody @Valid RiverLevelChartFilter filter) {
        return waterChartsService.getRiverLevelData(filter);
    }
}
