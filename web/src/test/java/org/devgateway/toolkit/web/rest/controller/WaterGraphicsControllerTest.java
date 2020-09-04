package org.devgateway.toolkit.web.rest.controller;

import static org.hamcrest.Matchers.is;
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

import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChart;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.service.charts.WaterChartsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WaterGraphicsController.class)
public class WaterGraphicsControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private WaterChartsService waterChartsService;

    private SampleWaterData waterData;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        SampleCommonData commonData = new SampleCommonData();
        waterData = new SampleWaterData(commonData);
    }

    @Test
    public void getAllWaterCharts() throws Exception {
        given(waterChartsService.getCharts())
                .willReturn(waterData.getChartsData());

        mvc.perform(get("/api/graphics/water/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("commonConfig").isNotEmpty())
                .andExpect(jsonPath("waterConfig").isNotEmpty())
                .andExpect(jsonPath("rainLevelChart").isNotEmpty())
                .andExpect(jsonPath("drySequenceChart").isNotEmpty())
                .andExpect(jsonPath("seasonChart").isNotEmpty())
                .andExpect(jsonPath("riverLevelChart").isNotEmpty())
                .andDo(document("all-water-charts",
                        responseFields(
                                subsectionWithPath("commonConfig")
                                        .description("<<common-config,Configuration>> for all charts"),
                                subsectionWithPath("waterConfig")
                                        .description("<<water-charts-config,Configuration>> for water based charts"),
                                subsectionWithPath("rainLevelChart")
                                        .description("<<rainfall-chart,Rainfall chart>>"),
                                subsectionWithPath("drySequenceChart")
                                        .description("<<dry-sequence-chart,Dry sequence chart>>"),
                                subsectionWithPath("seasonChart")
                                        .description("<<rainfall-season-chart,Rainfall season chart>>"),
                                subsectionWithPath("riverLevelChart")
                                        .description("<<river-level-chart,River level chart>>")),
                        responseFields(
                                beneathPath("rainLevelChart").withSubsectionId("rainfall"),
                                subsectionWithPath("config")
                                        .description("<<rainfall-chart-config,Configuration>>"),
                                subsectionWithPath("filter")
                                        .description("Default <<rainfall-chart-data,filter for rainfall chart>>"),
                                subsectionWithPath("data")
                                        .description("<<rainfall-chart-data,Data>> for the default filter")),
                        responseFields(
                                beneathPath("drySequenceChart").withSubsectionId("drySequence"),
                                subsectionWithPath("filter")
                                        .description("Default <<dry-sequence-chart-data,filter>>"),
                                subsectionWithPath("data")
                                        .description("<<dry-sequence-chart-data,Data>> for the default filter")),
                        responseFields(
                                beneathPath("seasonChart").withSubsectionId("rainfallSeason"),
                                subsectionWithPath("config")
                                        .description("<<rainfall-season-chart-config,Configuration>>"),
                                subsectionWithPath("filter")
                                        .description("Default <<rainfall-season-chart-data,filter>>"),
                                subsectionWithPath("data")
                                        .description("<<rainfall-season-chart-data,Data>> for the default filter")),
                        responseFields(
                                beneathPath("riverLevelChart").withSubsectionId("riverLevel"),
                                subsectionWithPath("config")
                                        .description("<<river-level-chart-config,Configuration>>"),
                                subsectionWithPath("filter")
                                        .description("Default <<post river-level-chart-data,filter>>"),
                                subsectionWithPath("data")
                                        .description("<<river-level-chart-data,Data>> for the default filter"))));
    }

    @Test
    public void getWaterConfig() throws Exception {
        given(waterChartsService.getWaterConfig()).willReturn(waterData.getChartsData().getWaterConfig());

        mvc.perform(get("/api/graphics/water/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("posts").isNotEmpty())
                .andDo(document("water-config",
                        responseFields(
                                subsectionWithPath("posts").description("<<pluviometric-post,Pluviometric Posts>>")),
                        responseFields(
                                beneathPath("posts"),
                                fieldWithPath("id").description("Pluviometric Post Id"),
                                fieldWithPath("label").description("Name"),
                                fieldWithPath("latitude").description("Latitude in decimal degrees"),
                                fieldWithPath("longitude").description("Longitude in decimal degrees"),
                                fieldWithPath("departmentId").description("Department Id"))));
    }

    @Test
    public void getRainLevelData() throws Exception {
        RainLevelChart rainLevelChart = waterData.getChartsData().getRainLevelChart();

        given(waterChartsService.getRainLevelData(any())).willReturn(rainLevelChart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(RainLevelChartFilter.class);

        mvc.perform(post("/api/graphics/water/rain-level/data").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(rainLevelChart.getFilter())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("levels").isNotEmpty())
                .andExpect(jsonPath("referenceLevels").isNotEmpty())
                .andDo(document("rain-level-data",
                        requestFields(
                                constrainedFields.withPath("years").description("Years"),
                                constrainedFields.withPath("pluviometricPostId")
                                        .description("<<pluviometric-post,Pluviometric Post>> Id")),
                        responseFields(
                                subsectionWithPath("levels")
                                        .description("<<rainfall-levels,Rainfall levels>>"),
                                subsectionWithPath("referenceLevels")
                                        .description("<<reference-rainfalls,Reference rainfalls>>")),
                        responseFields(
                                beneathPath("levels").withSubsectionId("levels"),
                                fieldWithPath("value").description("Rainfall in cm"),
                                fieldWithPath("year").description("Year"),
                                fieldWithPath("month").description("Month"),
                                fieldWithPath("decadal").description("Decadal")),
                        responseFields(
                                beneathPath("referenceLevels").withSubsectionId("refLevelsGroup"),
                                fieldWithPath("yearStart").description("Start year of rainfall levels to compare with"),
                                fieldWithPath("yearEnd").description("End year of rainfall levels to compare with"),
                                fieldWithPath("referenceYearStart")
                                        .description("Start year of the reference rainfall levels"),
                                fieldWithPath("referenceYearEnd")
                                        .description("End year of the reference rainfall levels"),
                                subsectionWithPath("levels")
                                        .description("<<reference-rainfall-levels,Rainfall reference levels>>")),
                        responseFields(
                                beneathPath("referenceLevels[].levels").withSubsectionId("refLevels"),
                                fieldWithPath("[].value").description("Rainfall in cm"),
                                fieldWithPath("[].month").description("Month"),
                                fieldWithPath("[].decadal").description("Decadal"))));
    }

    @Test
    public void getRainLevelConfig() throws Exception {
        RainLevelChart rainLevelChart = waterData.getChartsData().getRainLevelChart();

        given(waterChartsService.getRainLevelConfig()).willReturn(rainLevelChart.getConfig());

        mvc.perform(get("/api/graphics/water/rain-level/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andExpect(jsonPath("pluviometricPostIds").isNotEmpty())
                .andDo(document("rain-level-config",
                        responseFields(
                                fieldWithPath("years").description("Years with rainfall data"),
                                fieldWithPath("pluviometricPostIds")
                                        .description("Pluviometric posts with rainfall data"))));
    }

    @Test
    public void getDrySequenceData() throws Exception {
        DrySequenceChart drySequenceChart = waterData.getChartsData().getDrySequenceChart();

        given(waterChartsService.getDrySequenceData(any())).willReturn(drySequenceChart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(DrySequenceChartFilter.class);

        mvc.perform(post("/api/graphics/water/dry-sequence/data").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(drySequenceChart.getFilter())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("daysWithRain").isNotEmpty())
                .andDo(document("dry-sequence-data",
                        requestFields(
                                constrainedFields.withPath("year").description("Year"),
                                constrainedFields.withPath("pluviometricPostId")
                                        .description("<<pluviometric-post,Pluviometric Post>> Id")),
                        responseFields(
                                subsectionWithPath("daysWithRain").description("<<days-with-rain,Days with rain>>")),
                        responseFields(
                                beneathPath("daysWithRain").withSubsectionId("daysWithRain"),
                                fieldWithPath("month").description("Month"),
                                fieldWithPath("decadal").description("Decadal"),
                                fieldWithPath("daysWithRain").description("Number of days on which there was at "
                                        + "least 1 mm of rainfall"),
                                fieldWithPath("daysWithoutRain").description("Number of days without rain"))));
    }

    @Test
    public void getRainSeasonConfig() throws Exception {
        SeasonChart seasonChart = waterData.getChartsData().getSeasonChart();

        given(waterChartsService.getRainSeasonConfig()).willReturn(seasonChart.getConfig());

        mvc.perform(get("/api/graphics/water/rain-season/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andDo(document("rain-season-config",
                        responseFields(fieldWithPath("years").description("Years with rainfall data"))));
    }

    @Test
    public void getRainSeasonData() throws Exception {
        SeasonChart seasonChart = waterData.getChartsData().getSeasonChart();

        given(waterChartsService.getRainSeasonData(any())).willReturn(seasonChart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(SeasonChartFilter.class);

        mvc.perform(post("/api/graphics/water/rain-season/data").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(seasonChart.getFilter())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("yearStart", is(1981)))
                .andExpect(jsonPath("yearEnd", is(2020)))
                .andExpect(jsonPath("referenceYearStart", is(1981)))
                .andExpect(jsonPath("referenceYearEnd", is(2010)))
                .andExpect(jsonPath("predictions").isNotEmpty())
                .andDo(document("rain-season-data",
                        requestFields(
                                constrainedFields.withPath("year").description("Year")),
                        responseFields(
                                fieldWithPath("yearStart")
                                        .description("Start year of seasonal predictions to compare with"),
                                fieldWithPath("yearEnd")
                                        .description("End year of seasonal predictions to compare with"),
                                fieldWithPath("referenceYearStart")
                                        .description("Start year of the reference seasonal predictions"),
                                fieldWithPath("referenceYearEnd")
                                        .description("End year of the reference seasonal predictions"),
                                subsectionWithPath("predictions")
                                        .description("<<season-prediction,Seasonal predictions>>")),
                        responseFields(
                                beneathPath("predictions").withSubsectionId("predictions"),
                                fieldWithPath("pluviometricPostId")
                                        .description("<<pluviometric-post,Pluviometric Post>> Id"),
                                fieldWithPath("actual")
                                        .description("Actual date of rain season start. Format is --mm-dd."),
                                fieldWithPath("planned")
                                        .description("Predicted date of rain season start. Format is --mm-dd."),
                                fieldWithPath("difference")
                                        .description("Difference in days between actual and planned dates. "
                                                + "May be negative if season started before the predicted date."))));
    }

    @Test
    public void getRiverLevelConfig() throws Exception {
        RiverLevelChart riverLevelChart = waterData.getChartsData().getRiverLevelChart();

        given(waterChartsService.getRiverLevelConfig()).willReturn(riverLevelChart.getConfig());

        mvc.perform(get("/api/graphics/water/river-level/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andExpect(jsonPath("riverStations").isNotEmpty())
                .andDo(document("river-level-config",
                        responseFields(
                                fieldWithPath("years").description("Years with rainfall data"),
                                subsectionWithPath("riverStations").description("<<river-station,River stations>>")),
                        responseFields(
                                beneathPath("riverStations").withSubsectionId("riverStations"),
                                fieldWithPath("id").description("River Station Id"),
                                fieldWithPath("name").description("River station name"),
                                fieldWithPath("river").description("River name"),
                                fieldWithPath("alertLevel").description("Alert level in cm"))));
    }

    @Test
    public void getRiverLevelData() throws Exception {
        RiverLevelChart riverLevelChart = waterData.getChartsData().getRiverLevelChart();

        given(waterChartsService.getRiverLevelData(any())).willReturn(riverLevelChart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(RiverLevelChartFilter.class);

        mvc.perform(post("/api/graphics/water/river-level/data").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(riverLevelChart.getFilter())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("yearlyLevels").isNotEmpty())
                .andExpect(jsonPath("referenceYearlyLevels").isNotEmpty())
                .andDo(document("river-level-data",
                        requestFields(
                                constrainedFields.withPath("years").description("Years"),
                                constrainedFields.withPath("riverStationId")
                                        .description("<<river-station,River Station>> Id")),
                        responseFields(
                                subsectionWithPath("yearlyLevels")
                                        .description("<<annual-river-levels,Annual river levels>>"),
                                subsectionWithPath("referenceYearlyLevels")
                                        .description("<<annual-river-levels,Annual reference river levels>>")),
                        responseFields(
                                beneathPath("yearlyLevels").withSubsectionId("annualLevels"),
                                fieldWithPath("year").description("Year"),
                                subsectionWithPath("levels").description("<<river-levels,River levels>>")),
                        responseFields(
                                beneathPath("yearlyLevels[].levels").withSubsectionId("levels"),
                                fieldWithPath("[].monthDay").description("Month-day. Format is --mm-dd."),
                                fieldWithPath("[].level").description("River level in cm"))));
    }
}
