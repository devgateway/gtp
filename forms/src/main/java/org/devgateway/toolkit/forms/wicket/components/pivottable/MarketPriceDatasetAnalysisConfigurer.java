package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toMap;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.ipar.Market;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.service.ipar.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.MarketService;

/**
 * @author Octavian Ciubotaru
 */
public class MarketPriceDatasetAnalysisConfigurer implements DatasetAnalysisConfigurer {

    private MarketService marketService;
    private CropTypeService cropTypeService;

    public MarketPriceDatasetAnalysisConfigurer(MarketService marketService, CropTypeService cropTypeService) {
        this.marketService = marketService;
        this.cropTypeService = cropTypeService;
    }

    @Override
    public List<String> getCols() {
        return ImmutableList.of("cropTypeName");
    }

    @Override
    public List<String> getRows() {
        return ImmutableList.of("marketName");
    }

    @Override
    public List<String> getVals() {
        return ImmutableList.of("wholesaleBuyPrice");
    }

    @Override
    public String getAggregatorName() {
        return "average";
    }

    @Override
    public List<PivotField> getExtraFields() {
        return ImmutableList.of(
                new PivotField("cropTypeName", true, false),
                new PivotField("department", true, false),
                new PivotField("region", true, false),
                new PivotField("regionCode", true, false),
                new PivotField("marketName", true, false),
                new PivotField("weekDay", true, false),
                new PivotField("month", true, false),
                new PivotField("quarter", true, false));
    }

    @Override
    public Object getExtraOpts(String language) {

        MarketPriceOpts opts = new MarketPriceOpts();

        List<Market> markets = marketService.findAll();
        opts.setRegionNames(
                markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getRegion().getName())));

        opts.setRegionCodes(
                markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getRegion().getCode())));

        opts.setDepartmentNames(markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getName())));

        opts.setMarketNames(markets.stream().collect(toMap(Market::getId, Market::getName)));

        List<CropType> cropTypes = cropTypeService.findAll();
        opts.setCropTypeNames(cropTypes.stream().collect(toMap(CropType::getId, ct -> ct.getLocalizedLabel(language))));

        return opts;
    }
}
