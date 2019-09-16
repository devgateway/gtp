package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toMap;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.repository.MarketRepository;

/**
 * @author Octavian Ciubotaru
 */
public class MarketPriceDatasetAnalysisConfigurer implements DatasetAnalysisConfigurer {

    private MarketRepository marketRepository;

    public MarketPriceDatasetAnalysisConfigurer(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    @Override
    public List<String> getCols() {
        return ImmutableList.of("crop");
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
                new PivotField("department", true, false),
                new PivotField("region", true, false),
                new PivotField("regionCode", true, false),
                new PivotField("marketName", true, false),
                new PivotField("weekDay", true, false),
                new PivotField("month", true, false),
                new PivotField("quarter", true, false),
                new PivotField("year", true, false));
    }

    @Override
    public Object getExtraOpts() {
        List<Market> markets = marketRepository.findAll();

        MarketPriceOpts opts = new MarketPriceOpts();

        opts.setRegionNames(
                markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getRegion().getName())));

        opts.setRegionCodes(
                markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getRegion().getCode())));

        opts.setDepartmentNames(markets.stream().collect(toMap(Market::getId, m -> m.getDepartment().getName())));

        opts.setMarketNames(markets.stream().collect(toMap(Market::getId, Market::getName)));

        return opts;
    }
}
