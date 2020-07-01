package org.devgateway.toolkit.web.rest.controller;

import java.math.BigDecimal;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;
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
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;

/**
 * @author Octavian Ciubotaru
 */
public class SampleAgricultureData {

    private final AgricultureChartsData agricultureChartsData;

    private final Product rice;
    private final Product sorghum;

    private final Market gueuleTapee;

    private final PriceType retailPrice;
    private final PriceType wholesalePrice;

    private final ProductType cereals;

    public SampleAgricultureData(SampleCommonData commonData) {

        MarketType ruralMarket = new MarketType(1L, MarketType.RURAL_MARKET, "Rural market");
        MarketType fishingDock = new MarketType(2L, MarketType.FISHING_DOCK, "Fishing dock");
        MarketType transformationPlace = new MarketType(3L, MarketType.TRANSFORMATION_PLACE, "Transformation place");
        List<MarketType> marketTypes = ImmutableList.of(ruralMarket, fishingDock, transformationPlace);

        gueuleTapee = new Market(1L, commonData.getDepartmentKedougou(), "Gueule tapée", ruralMarket,
                MarketDaysUtil.ALL_DAYS, 12.0, -12.0);
        List<Market> markets = ImmutableList.of(gueuleTapee);

        wholesalePrice = new PriceType(1L, "wholesale-price", "Wholesale price");
        retailPrice = new PriceType(2L, "retail-price", "Retail price");
        List<PriceType> priceTypes = ImmutableList.of(wholesalePrice, retailPrice);

        cereals = new ProductType(1L, ProductType.CEREALS, "Cereals", ruralMarket);
        ProductType vegetables = new ProductType(2L, ProductType.VEGETABLES, "Vegetables", ruralMarket);
        List<ProductType> productTypes = ImmutableList.of(cereals, vegetables);

        MeasurementUnit kg = new MeasurementUnit(1L, "kg", "Kg");

        rice = new Product(1L, cereals, "Rice", kg, priceTypes);
        sorghum = new Product(2L, cereals, "Sorghum", kg, priceTypes);
        Product potato = new Product(3L, vegetables, "Potato", kg, priceTypes);
        List<Product> products = ImmutableList.of(rice, sorghum, potato);

        AgricultureConfig agricultureConfig =
                new AgricultureConfig(marketTypes, markets, productTypes, products, priceTypes);

        ProductPricesChart productPricesChart = getProductPricesChart();

        ProductQuantitiesChart productQuantitiesChart = getProductQuantitiesChart();

        agricultureChartsData = new AgricultureChartsData(commonData.getCommonConfig(), agricultureConfig,
                productPricesChart, productQuantitiesChart);
    }

    private ProductQuantitiesChart getProductQuantitiesChart() {
        ProductQuantitiesChartConfig config = new ProductQuantitiesChartConfig(ImmutableSortedSet.of(2018, 2019, 2020));

        ProductQuantitiesChartFilter filter =
                new ProductQuantitiesChartFilter(2020, cereals.getId(), gueuleTapee.getId());

        ProductQuantitiesChartData data = new ProductQuantitiesChartData(ImmutableList.of(
                new ProductQuantity(rice, gueuleTapee, MonthDay.of(1, 3), new BigDecimal("10.5")),
                new ProductQuantity(rice, gueuleTapee, MonthDay.of(1, 10), new BigDecimal("23")),
                new ProductQuantity(rice, gueuleTapee, MonthDay.of(1, 17), new BigDecimal("14")),
                new ProductQuantity(sorghum, gueuleTapee, MonthDay.of(1, 6), new BigDecimal("67")),
                new ProductQuantity(sorghum, gueuleTapee, MonthDay.of(1, 13), new BigDecimal("125")),
                new ProductQuantity(sorghum, gueuleTapee, MonthDay.of(1, 20), new BigDecimal("300"))));

        return new ProductQuantitiesChart(config, filter, data);
    }

    private ProductPricesChart getProductPricesChart() {
        ProductPricesChartConfig config = new ProductPricesChartConfig(ImmutableSortedSet.of(2018, 2019, 2020));

        ProductPricesChartFilter filter = new ProductPricesChartFilter(config.getYears().last(), rice.getId(),
                gueuleTapee.getId());

        List<ProductPrice> prices = ImmutableList.of(
                new ProductPrice(rice, gueuleTapee, MonthDay.of(Month.JANUARY, 1), retailPrice, 100),
                new ProductPrice(rice, gueuleTapee, MonthDay.of(Month.JANUARY, 1), wholesalePrice, 90),
                new ProductPrice(rice, gueuleTapee, MonthDay.of(Month.JANUARY, 9), retailPrice, 102),
                new ProductPrice(rice, gueuleTapee, MonthDay.of(Month.JANUARY, 9), wholesalePrice, 91));

        List<AveragePrice> previousYearAverages = ImmutableList.of(
                new AveragePrice(retailPrice, 105.67),
                new AveragePrice(wholesalePrice, 93.33));

        ProductPricesChartData data = new ProductPricesChartData(prices, previousYearAverages);

        return new ProductPricesChart(config, filter, data);
    }

    public AgricultureChartsData getAgricultureChartsData() {
        return agricultureChartsData;
    }
}
