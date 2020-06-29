package org.devgateway.toolkit.persistence.dto.agriculture;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;

/**
 * @author Octavian Ciubotaru
 */
public class AgricultureConfig {

    private final List<MarketType> marketTypes;
    private final List<Market> markets;
    private final List<ProductType> productTypes;
    private final List<Product> products;
    private final List<PriceType> priceTypes;

    public AgricultureConfig(List<MarketType> marketTypes, List<Market> markets,
            List<ProductType> productTypes,
            List<Product> products,
            List<PriceType> priceTypes) {
        this.marketTypes = marketTypes;
        this.markets = markets;
        this.productTypes = productTypes;
        this.products = products;
        this.priceTypes = priceTypes;
    }

    public List<MarketType> getMarketTypes() {
        return marketTypes;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<PriceType> getPriceTypes() {
        return priceTypes;
    }
}
