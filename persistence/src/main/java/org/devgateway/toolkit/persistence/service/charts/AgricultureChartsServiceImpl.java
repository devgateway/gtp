package org.devgateway.toolkit.persistence.service.charts;

import static java.util.stream.Collectors.toCollection;

import com.google.common.collect.ImmutableList;
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
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChart;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartFilter;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.MarketTypeService;
import org.devgateway.toolkit.persistence.service.category.PriceTypeService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

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
        AgricultureConfig agricultureConfig = getAgricultureConfig();
        return new AgricultureChartsData(chartService.getCommonConfig(), getAgricultureConfig(),
                getProductPricesChart(agricultureConfig), getProductQuantitiesChart(agricultureConfig));
    }

    private ProductPricesChart getProductPricesChart(AgricultureConfig agricultureConfig) {
        ProductPricesChartConfig config = getProductPricesChartConfig();

        ProductPricesChartFilter filter = getProductPricesChartFilter(config, agricultureConfig);

        ProductPricesChartData data;
        if (filter.getMarketId() != null && filter.getProductId() != null) {
            data = getProductPricesChartData(filter);
        } else {
            data = new ProductPricesChartData(ImmutableList.of(), ImmutableList.of());
        }

        return new ProductPricesChart(config, filter, data);
    }

    private ProductPricesChartFilter getProductPricesChartFilter(ProductPricesChartConfig config,
            AgricultureConfig agricultureConfig) {
        Integer year = config.getYears().isEmpty()
                ? Year.now(AD3Clock.systemDefaultZone()).getValue()
                : config.getYears().last();

        Long productId;
        Product defaultProduct = adminSettingsService.get().getDefaultProduct();
        if (defaultProduct != null && productYearlyPricesService.hasPrices(year, defaultProduct.getId())) {
            productId = defaultProduct.getId();
        } else {
            Long productIdWithPrices = productYearlyPricesService.getProductIdWithPrices(year);
            if (productIdWithPrices == null) {
                productId = defaultProduct != null ? defaultProduct.getId() : getFirstProduct(agricultureConfig);
            } else {
                productId = productIdWithPrices;
            }
        }

        Long marketId = null;
        if (productId != null) {
            marketId = productYearlyPricesService.getMarketIdWithPrices(year, productId);
        }

        return new ProductPricesChartFilter(year, productId, marketId);
    }

    private Long getFirstProduct(AgricultureConfig agricultureConfig) {
        return agricultureConfig.getProducts().isEmpty() ? null : agricultureConfig.getProducts().get(0).getId();
    }

    @Override
    @Transactional
    public ProductPricesChartConfig getProductPricesChartConfig() {
        return new ProductPricesChartConfig(
                filterYears(productYearlyPricesService.findYearsWithPrices()));
    }

    @Override
    @Transactional
    public ProductPricesChartData getProductPricesChartData(ProductPricesChartFilter filter) {
        List<ProductPrice> prices = productYearlyPricesService.findPrices(filter);
        List<AveragePrice> avgPrices = productYearlyPricesService.findPreviousYearAveragePrices(filter);
        return new ProductPricesChartData(prices, avgPrices);
    }

    private ProductQuantitiesChart getProductQuantitiesChart(AgricultureConfig agricultureConfig) {
        ProductQuantitiesChartConfig config = getProductQuantitiesChartConfig();

        ProductQuantitiesChartFilter filter = getProductQuantitiesChartFilter(config, agricultureConfig);

        ProductQuantitiesChartData data;
        if (filter.getProductTypeId() == null || filter.getMarketId() == null) {
            data = new ProductQuantitiesChartData(ImmutableList.of());
        } else {
            data = getProductQuantitiesChartData(filter);
        }

        return new ProductQuantitiesChart(config, filter, data);
    }

    @Override
    public ProductQuantitiesChartData getProductQuantitiesChartData(ProductQuantitiesChartFilter filter) {
        return new ProductQuantitiesChartData(
                productYearlyPricesService.findQuantities(filter));
    }

    private ProductQuantitiesChartFilter getProductQuantitiesChartFilter(ProductQuantitiesChartConfig config,
            AgricultureConfig agricultureConfig) {

        int year = config.getYears().isEmpty()
                ? Year.now(AD3Clock.systemDefaultZone()).getValue()
                : config.getYears().last();

        Long productTypeId = null;
        ProductType defaultProductType = adminSettingsService.get().getDefaultProductType();
        if (defaultProductType != null) {
            Long defaultProductTypeId = defaultProductType.getId();
            Long id = productYearlyPricesService.getMarketIdWithQuantities(year, defaultProductTypeId);
            if (id != null) {
                productTypeId = defaultProductTypeId;
            }
        }
        if (productTypeId == null) {
            productTypeId = productYearlyPricesService.getProductTypeIdWithQuantities(year);
        }
        if (productTypeId == null) {
            productTypeId = getFirstProductType(agricultureConfig);
        }

        Long marketId = null;
        if (productTypeId != null) {
            marketId = productYearlyPricesService.getMarketIdWithQuantities(year, productTypeId);
        }

        return new ProductQuantitiesChartFilter(year, productTypeId, marketId);
    }

    private Long getFirstProductType(AgricultureConfig agricultureConfig) {
        List<ProductType> productTypes = agricultureConfig.getProductTypes();
        return productTypes.isEmpty() ? null : productTypes.get(0).getId();
    }

    @Override
    @Transactional
    public ProductQuantitiesChartConfig getProductQuantitiesChartConfig() {
        return new ProductQuantitiesChartConfig(
                filterYears(productYearlyPricesService.findYearsWithQuantities()));
    }

    private TreeSet<Integer> filterYears(Collection<Integer> years) {
        Integer minYear = adminSettingsService.getStartingYear();
        return years.stream()
                .filter(y -> y >= minYear)
                .collect(toCollection(TreeSet::new));
    }
}
