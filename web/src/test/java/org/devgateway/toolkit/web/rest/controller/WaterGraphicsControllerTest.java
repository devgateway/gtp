package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.service.WaterChartsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WaterGraphicsController.class)
public class WaterGraphicsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WaterChartsService waterChartsService;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        ChartsData chartsData = new ChartsData(null, null, null, null, null, null);

        BDDMockito.given(waterChartsService.getCharts())
                .willReturn(chartsData);

        mvc.perform(get("/api/graphics/water/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
