package org.devgateway.toolkit.web.rest.controller;

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
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChart;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;

/**
 * @author Octavian Ciubotaru
 */
public class SampleAgricultureData {

    private final AgricultureChartsData agricultureChartsData;

    private final Product rice;

    private final Market gueuleTapee;

    private final PriceType retailPrice;
    private final PriceType wholesalePrice;

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

        ProductType cereals = new ProductType(1L, ProductType.CEREALS, "Cereals");
        ProductType vegetables = new ProductType(2L, ProductType.VEGETABLES, "Vegetables");
        List<ProductType> productTypes = ImmutableList.of(cereals, vegetables);

        MeasurementUnit kg = new MeasurementUnit(1L, "kg", "Kg");

        rice = new Product(1L, cereals, "Rice", kg, priceTypes);
        Product sorghum = new Product(2L, cereals, "Sorghum", kg, priceTypes);
        Product potato = new Product(3L, vegetables, "Potato", kg, priceTypes);
        List<Product> products = ImmutableList.of(rice, sorghum, potato);

        AgricultureConfig agricultureConfig =
                new AgricultureConfig(marketTypes, markets, productTypes, products, priceTypes);

        ProductPricesChart productPricesChart = getProductPricesChart();

        agricultureChartsData = new AgricultureChartsData(commonData.getCommonConfig(), agricultureConfig,
                productPricesChart);
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
