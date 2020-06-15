package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.toCollection;

import java.util.List;
import java.util.TreeSet;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChart;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.MarketTypeService;
import org.devgateway.toolkit.persistence.service.category.PriceTypeService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class AgricultureChartsServiceImpl implements AgricultureChartsService {

    @Autowired
    private MarketService marketService;

    @Autowired
    private MarketTypeService marketTypeService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private PriceTypeService priceTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductYearlyPricesService productYearlyPricesService;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private ChartService chartService;

    @Override
    @Transactional
    public AgricultureConfig getAgricultureConfig() {
        List<Market> markets = marketService.findAll();
        List<MarketType> marketTypes = marketTypeService.findAll();
        List<ProductType> productTypes = productTypeService.findAll();
        List<Product> products = productService.findAll();
        List<PriceType> priceTypes = priceTypeService.findAll();
        return new AgricultureConfig(marketTypes, markets, productTypes, products, priceTypes);
    }

    @Override
    @Transactional
    public AgricultureChartsData getAgricultureChartsData() {
        return new AgricultureChartsData(chartService.getCommonConfig(), getAgricultureConfig(),
                getProductPricesChart());
    }

    private ProductPricesChart getProductPricesChart() {
        ProductPricesChartConfig config = getProductPricesChartConfig();

        ProductPricesChartFilter filter = getProductPricesChartFilter(config);

        ProductPricesChartData data = null;
        if (filter.getYear() != null && filter.getMarketId() != null && filter.getProductId() != null) {
            data = getProductPricesChartData(filter);
        }

        return new ProductPricesChart(config, filter, data);
    }

    private ProductPricesChartFilter getProductPricesChartFilter(ProductPricesChartConfig config) {
        Integer year = config.getYears().isEmpty() ? null : config.getYears().last();

        Product defaultProduct = adminSettingsService.get().getDefaultProduct();
        Long productId = defaultProduct != null ? defaultProduct.getId() : null;
        if (year != null && productId != null && !productYearlyPricesService.hasPrices(year, productId)) {
            productId = productYearlyPricesService.getProductIdWithPrices(year);
        }

        Long marketId = null;
        if (year != null && productId != null) {
            marketId = productYearlyPricesService.getMarketIdWithPrices(year, productId);
        }

        return new ProductPricesChartFilter(year, productId, marketId);
    }

    @Override
    @Transactional
    public ProductPricesChartConfig getProductPricesChartConfig() {
        Integer minYear = adminSettingsService.getStartingYear();
        TreeSet<Integer> years = productYearlyPricesService.findYearsWithPrices().stream()
                .filter(y -> y >= minYear)
                .collect(toCollection(TreeSet::new));

        return new ProductPricesChartConfig(years);
    }

    @Override
    @Transactional
    public ProductPricesChartData getProductPricesChartData(ProductPricesChartFilter filter) {
        List<ProductPrice> prices = productYearlyPricesService.findPrices(filter);
        List<AveragePrice> avgPrices = productYearlyPricesService.findPreviousYearAveragePrices(filter);
        return new ProductPricesChartData(prices, avgPrices);
    }
}
