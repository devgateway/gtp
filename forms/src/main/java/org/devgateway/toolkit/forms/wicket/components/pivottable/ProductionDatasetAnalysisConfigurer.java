package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
import org.devgateway.toolkit.persistence.repository.category.CropTypeRepository;

/**
 * @author Octavian Ciubotaru
 */
public class ProductionDatasetAnalysisConfigurer implements DatasetAnalysisConfigurer {

    private CropTypeRepository cropTypeRepository;

    private RegionRepository regionRepository;

    public ProductionDatasetAnalysisConfigurer(
            RegionRepository regionRepository,
            CropTypeRepository cropTypeRepository) {
        this.regionRepository = regionRepository;
        this.cropTypeRepository = cropTypeRepository;
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
                new PivotField("campaign", true, false),
                new PivotField("cropTypeName", true, false),
                new PivotField("regionName", true, false),
                new PivotField("regionCode", true, false));
    }

    @Override
    public Object getExtraOpts(String language) {
        ProductionOpts opts = new ProductionOpts();

        List<Region> regions = regionRepository.findAll();
        opts.setRegionNames(regions.stream().collect(Collectors.toMap(Region::getId, Region::getName)));
        opts.setRegionCodes(regions.stream().collect(Collectors.toMap(Region::getId, Region::getCode)));

        List<CropType> cropTypes = cropTypeRepository.findAll();
        opts.setCropTypeNames(cropTypes.stream()
                .collect(toMap(CropType::getId, ct -> ct.getLocalizedLabel(language))));

        return opts;
    }
}
