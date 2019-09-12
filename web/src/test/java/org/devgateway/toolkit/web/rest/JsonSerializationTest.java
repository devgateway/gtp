package org.devgateway.toolkit.web.rest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.devgateway.toolkit.persistence.dao.Market;
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
    private JacksonTester<Market> marketJacksonTester;

    /**
     * Ensure that only necessary fields are included in output. We want to keep output as compact as possible.
     */
    @Test
    public void testMarketWithAllValuesSpecified() throws IOException {
        Market market = new Market(1L, "TH", "THIES", "TOUBA TOUL", LocalDate.parse("2018-11-01"), "MILLET");
        market.setQuantity(3d);
        market.setSellPrice(225d);
        market.setDetailBuyPrice(250d);
        market.setWholesaleBuyPrice(240d);

        JsonContent<Market> content = marketJacksonTester.write(market);

        assertEquals("{\"region\":\"TH\","
                + "\"department\":\"THIES\","
                + "\"market\":\"TOUBA TOUL\","
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
        Market market = new Market();

        assertEquals("{}", marketJacksonTester.write(market).getJson());
    }
}
