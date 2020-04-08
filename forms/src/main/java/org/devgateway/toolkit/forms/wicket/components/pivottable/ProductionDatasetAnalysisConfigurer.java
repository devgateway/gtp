package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.service.ipar.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.location.RegionService;

/**
 * @author Octavian Ciubotaru
 */
public class ProductionDatasetAnalysisConfigurer implements DatasetAnalysisConfigurer {

    private RegionService regionService;
    private CropTypeService cropTypeService;

    public ProductionDatasetAnalysisConfigurer(
            RegionService regionService, CropTypeService cropTypeService) {
        this.regionService = regionService;
        this.cropTypeService = cropTypeService;
    }

    @Override
    public List<String> getCols() {
        return ImmutableList.of("cropTypeName");
    }

    @Override
    public List<String> getRows() {
        return ImmutableList.of("regionName");
    }

    @Override
    public List<String> getVals() {
        return ImmutableList.of("production");
    }

    @Override
    public String getAggregatorName() {
        return "sum";
    }

    @Override
    public List<PivotField> getExtraFields() {
        return ImmutableList.of(
               // new PivotField("campaign", true, false),
                new PivotField("cropTypeName", true, false),
                new PivotField("regionName", true, false),
                new PivotField("regionCode", true, false));
    }

    @Override
    public Object getExtraOpts(String language) {
        ProductionOpts opts = new ProductionOpts();

        List<Region> regions = regionService.findAll();
        opts.setRegionNames(regions.stream().collect(Collectors.toMap(Region::getId, Region::getName)));
        opts.setRegionCodes(regions.stream().collect(Collectors.toMap(Region::getId, Region::getCode)));

        List<CropType> cropTypes = cropTypeService.findAll();
        opts.setCropTypeNames(cropTypes.stream()
                .collect(toMap(CropType::getId, ct -> ct.getLocalizedLabel(language))));

        return opts;
    }
}
