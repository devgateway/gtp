package org.devgateway.toolkit.web.rest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.web.spring.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebApplication.class })
@JsonTest
public class JsonSerializationTest {

    @Autowired
    private JacksonTester<MarketPrice> marketJacksonTester;

    @Autowired
    private JacksonTester<Production> productionJacksonTester;

    /**
     * Ensure that only necessary fields are included in output. We want to keep output as compact as possible.
     */
    @Test
    public void testMarketWithAllValuesSpecified() throws IOException {
        MarketPrice marketPrice = new MarketPrice();
        marketPrice.setDataset(new Dataset());
        marketPrice.setMarket(new Market(1L));
        marketPrice.setDate(LocalDate.parse("2018-11-01"));
        marketPrice.setCropType(new CropType(2L, "Millet"));
        marketPrice.setQuantity(3d);
        marketPrice.setSellPrice(225d);
        marketPrice.setDetailBuyPrice(250d);
        marketPrice.setWholesaleBuyPrice(240d);

        JsonContent<MarketPrice> content = marketJacksonTester.write(marketPrice);

        assertEquals("{\"market\":1,"
                + "\"cropType\":2,"
                + "\"date\":\"2018-11-01\","
                + "\"quantity\":3.0,"
                + "\"sellPrice\":225.0,"
                + "\"detailBuyPrice\":250.0,"
                + "\"wholesaleBuyPrice\":240.0}",
                content.getJson());
    }

    /**
     * Make sure that fields with null values are excluded from output for compactness.
     */
    @Test
    public void testMarketWithNullValues() throws IOException {
        MarketPrice marketPrice = new MarketPrice();

        assertEquals("{}", marketJacksonTester.write(marketPrice).getJson());
    }

    /**
     * Ensure that only necessary fields are included in output. We want to keep output as compact as possible.
     */
    @Test
    public void testProductionWithAllValuesSpecified() throws IOException {
        Production record = new Production();
        record.setDataset(new Dataset());
        record.setCropType(new CropType(2L, "Millet"));
        record.setYear(2018);
        record.setProduction(1.1d);
        record.setYield(0.5d);
        record.setSurface(201.9d);

        JsonContent<Production> content = productionJacksonTester.write(record);

        assertEquals("{\"cropType\":2,\"year\":2018,\"surface\":201.9,\"production\":1.1,\"yield\":0.5}",
                content.getJson());
    }

    /**
     * Make sure that fields with null values are excluded from output for compactness.
     */
    @Test
    public void testProductionWithNullValues() throws IOException {
        Production record = new Production();

        JsonContent<Production> content = productionJacksonTester.write(record);

        assertEquals("{}", content.getJson());
    }
}
