package org.devgateway.toolkit.web.rest.controller;

/**
 * @author Nadejda Mandrescu
 */

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityChart;
import org.devgateway.toolkit.persistence.dto.livestock.disease.DiseaseQuantityFilter;
import org.devgateway.toolkit.persistence.service.charts.LivestockChartsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(LivestockGraphicsController.class)
public class LivestockGraphicsControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private LivestockChartsService livestockChartsService;

    private SampleLivestockData sampleLivestockData;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        SampleCommonData sampleCommonData = new SampleCommonData();
        sampleLivestockData = new SampleLivestockData(sampleCommonData);
    }

    @Test
    public void getLivestockCharts() throws Exception {
        given(livestockChartsService.getLivestockCharts())
                .willReturn(sampleLivestockData.getLivestockCharts());

        mvc.perform(get("/api/graphics/livestock/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("commonConfig").isNotEmpty())
                .andExpect(jsonPath("livestockConfig").isNotEmpty())
                .andExpect(jsonPath("diseaseQuantityChart").isNotEmpty())
                .andDo(document("all-livestock-charts",
                        responseFields(
                                subsectionWithPath("commonConfig")
                                        .description("<<common-config,Configuration>> for all charts"),
                                subsectionWithPath("livestockConfig")
                                        .description("<<livestock-charts-config,Configuration>> for "
                                                + "livestock charts"),
                                subsectionWithPath("diseaseQuantityChart")
                                        .description("<<disease-quantity-chart,Disease quantity chart>>")),
                        responseFields(
                                beneathPath("diseaseQuantityChart").withSubsectionId("diseaseQuantityChart"),
                                subsectionWithPath("config")
                                        .description("<<disease-quantity-chart-config,Configuration>>"),
                                subsectionWithPath("filter")
                                        .description("Default <<disease-quantity-chart-data,filter>> "
                                        + "for disease quantity chart"),
                                subsectionWithPath("data")
                                        .description("<<disease-quantity-chart-data,Data>> for the default filter"))));
    }

    @Test
    public void getLivestockConfig() throws Exception {
        given(livestockChartsService.getLivestockConfig())
                .willReturn(sampleLivestockData.getLivestockCharts().getLivestockConfig());

        mvc.perform(get("/api/graphics/livestock/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("diseases").isNotEmpty())
                .andDo(document("livestock-config",
                        responseFields(
                                subsectionWithPath("diseases").description("<<disease,Diseases>>")),
                        responseFields(
                                beneathPath("diseases").withSubsectionId("disease"),
                                fieldWithPath("id").description("Livestock disease Id"),
                                fieldWithPath("name").description("Livestock disease name"),
                                fieldWithPath("label").description("User friendly name"))));
    }

    @Test
    public void getDiseaseQuantityChartConfig() throws Exception {
        given(livestockChartsService.getDiseaseQuantityConfig())
                .willReturn(sampleLivestockData.getLivestockCharts().getDiseaseQuantityChart().getConfig());

        mvc.perform(get("/api/graphics/livestock/disease-quantity/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andDo(document("disease-quantity-config",
                        responseFields(
                                fieldWithPath("years").description("Years with some disease recorded"))));
    }

    @Test
    public void getDiseaseQuantityChartData() throws Exception {
        DiseaseQuantityChart chart = sampleLivestockData.getLivestockCharts().getDiseaseQuantityChart();
        given(livestockChartsService.getDiseaseQuantityData(any()))
                .willReturn(chart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(DiseaseQuantityFilter.class);

        mvc.perform(post("/api/graphics/livestock/disease-quantity/data")
                .content(om.writeValueAsBytes(chart.getFilter()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("quantities").isNotEmpty())
                .andDo(document("disease-quantity-data",
                        requestFields(
                                constrainedFields.withPath("year").description("Year"),
                                constrainedFields.withPath("diseaseId").description("<<disease,Disease>> Id")),
                        responseFields(
                                subsectionWithPath("quantities").description("<<disease-quantity,Disease quantities>>" +
                                                " for the requested filter")),
                        responseFields(
                                beneathPath("quantities").withSubsectionId("diseaseQuantity"),
                                fieldWithPath("month").description("Month when the disease was recorded (1 - 12)"),
                                fieldWithPath("regionId").description("<<region,Region>> Id"),
                                fieldWithPath("quantity").description("Recorded disease quantity"))));

    }

}
