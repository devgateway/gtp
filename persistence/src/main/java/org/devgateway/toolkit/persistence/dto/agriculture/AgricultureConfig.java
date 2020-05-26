package org.devgateway.toolkit.persistence.dto.agriculture;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;

/**
 * @author Octavian Ciubotaru
 */
public class AgricultureConfig {

    private final List<MarketType> marketTypes;
    private final List<Market> markets;

    public AgricultureConfig(List<MarketType> marketTypes, List<Market> markets) {
        this.marketTypes = marketTypes;
        this.markets = markets;
    }

    public List<MarketType> getMarketTypes() {
        return marketTypes;
    }

    public List<Market> getMarkets() {
        return markets;
    }
}
