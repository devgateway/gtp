package org.devgateway.toolkit.web.rest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
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

    /**
     * Ensure that only necessary fields are included in output. We want to keep output as compact as possible.
     */
    @Test
    public void testMarketWithAllValuesSpecified() throws IOException {
        MarketPrice marketPrice = new MarketPrice();
        marketPrice.setMarket(new Market(1L));
        marketPrice.setDate(LocalDate.parse("2018-11-01"));
        marketPrice.setCrop("MILLET");
        marketPrice.setQuantity(3d);
        marketPrice.setSellPrice(225d);
        marketPrice.setDetailBuyPrice(250d);
        marketPrice.setWholesaleBuyPrice(240d);

        JsonContent<MarketPrice> content = marketJacksonTester.write(marketPrice);

        assertEquals("{\"market\":1,"
                + "\"crop\":\"MILLET\","
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
}
