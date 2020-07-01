package org.devgateway.toolkit.web.rest.controller;

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

import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChart;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChart;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartFilter;
import org.devgateway.toolkit.persistence.service.AgricultureChartsService;
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
@WebMvcTest(AgricultureGraphicsController.class)
public class AgricultureGraphicsControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private AgricultureChartsService agricultureChartsService;

    private SampleAgricultureData agricultureData;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        SampleCommonData commonData = new SampleCommonData();
        agricultureData = new SampleAgricultureData(commonData);
    }

    @Test
    public void getAgricultureChartsData() throws Exception {
        given(agricultureChartsService.getAgricultureChartsData())
                .willReturn(agricultureData.getAgricultureChartsData());

        mvc.perform(get("/api/graphics/agriculture/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("commonConfig").isNotEmpty())
                .andExpect(jsonPath("agricultureConfig").isNotEmpty())
                .andExpect(jsonPath("productPricesChart").isNotEmpty())
                .andExpect(jsonPath("productQuantitiesChart").isNotEmpty())
                .andDo(document("all-agriculture-charts",
                        responseFields(
                                subsectionWithPath("commonConfig")
                                        .description("<<common-config,Configuration>> for all charts"),
                                subsectionWithPath("agricultureConfig")
                                        .description("<<agriculture-charts-config,Configuration>> for "
                                                + "agriculture charts"),
                                subsectionWithPath("productPricesChart")
                                        .description("<<product-prices-chart,Product prices chart>>"),
                                subsectionWithPath("productQuantitiesChart")
                                        .description("<<product-quantities-chart,Product quantities chart>>")),
                        responseFields(
                                beneathPath("productPricesChart").withSubsectionId("productPricesChart"),
                                subsectionWithPath("config")
                                        .description("<<product-price-chart-config,Configuration>>"),
                                subsectionWithPath("filter").description("Default <<product-price-chart-data,filter>> "
                                        + "for product price chart"),
                                subsectionWithPath("data")
                                        .description("<<product-price-chart-data,Data>> for the default filter")),
                        responseFields(
                                beneathPath("productQuantitiesChart").withSubsectionId("productQuantitiesChart"),
                                subsectionWithPath("config")
                                        .description("<<product-quantity-chart-config,Configuration>>"),
                                subsectionWithPath("filter").description("Default <<product-quantity-chart-data,filter>> "
                                        + "for product quantity chart"),
                                subsectionWithPath("data")
                                        .description("<<product-quantity-chart-data,Data>> for the default filter"))));
    }

    @Test
    public void getAgricultureConfig() throws Exception {
        given(agricultureChartsService.getAgricultureConfig())
                .willReturn(agricultureData.getAgricultureChartsData().getAgricultureConfig());

        mvc.perform(get("/api/graphics/agriculture/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("marketTypes").isNotEmpty())
                .andExpect(jsonPath("markets").isNotEmpty())
                .andExpect(jsonPath("productTypes").isNotEmpty())
                .andExpect(jsonPath("priceTypes").isNotEmpty())
                .andExpect(jsonPath("products").isNotEmpty())
                .andDo(document("agriculture-config",
                        responseFields(
                                subsectionWithPath("products").description("<<product,Products>>"),
                                subsectionWithPath("productTypes").description("<<product-type,Product types>>"),
                                subsectionWithPath("priceTypes").description("<<price-type,Price types>>"),
                                subsectionWithPath("markets").description("<<market,Markets>>"),
                                subsectionWithPath("marketTypes").description("<<market-type,Market types>>")),
                        responseFields(
                                beneathPath("marketTypes").withSubsectionId("marketType"),
                                fieldWithPath("id").description("Market Type Id"),
                                fieldWithPath("name").description("Machine friendly name. One of: "
                                        + String.join(", ", MarketType.ALL) + "."),
                                fieldWithPath("label").description("User friendly name")),
                        responseFields(
                                beneathPath("markets").withSubsectionId("market"),
                                fieldWithPath("id").description("Market Id"),
                                fieldWithPath("name").description("Market name"),
                                fieldWithPath("marketDays").description("Days on which the market is open. "
                                        + "Format is 7 digits, first one standing for Monday (0=closed, 1=open)."),
                                fieldWithPath("latitude").description("Latitude in decimal degrees"),
                                fieldWithPath("longitude").description("Longitude in decimal degrees"),
                                fieldWithPath("departmentId")
                                        .description("<<department,Department>> Id"),
                                fieldWithPath("typeId").description("<<market-type,Market Type>> Id")),
                        responseFields(
                                beneathPath("productTypes").withSubsectionId("productType"),
                                fieldWithPath("id").description("Product Type Id"),
                                fieldWithPath("name")
                                        .description("Machine friendly name. One of: "
                                                + String.join(", ", ProductType.ALL) + "."),
                                fieldWithPath("label").description("User friendly name"),
                                fieldWithPath("marketTypeId").description("<<market-type,Market Type>> Id")),
                        responseFields(
                                beneathPath("priceTypes").withSubsectionId("priceType"),
                                fieldWithPath("id").description("Price Type Id"),
                                fieldWithPath("name").description("Machine friendly name"),
                                fieldWithPath("label").description("User friendly label")),
                        responseFields(
                                beneathPath("products").withSubsectionId("product"),
                                fieldWithPath("id").description("Product Id"),
                                fieldWithPath("name").description("Name"),
                                fieldWithPath("unit").description("Measurement unit. Either kg or head."),
                                fieldWithPath("productTypeId").description("<<product-type,Product Type>> Id"),
                                fieldWithPath("priceTypeIds").description("<<price-type,Price Type>> Ids"))));
    }

    @Test
    public void getProductPricesChartConfig() throws Exception {
        given(agricultureChartsService.getProductPricesChartConfig())
                .willReturn(agricultureData.getAgricultureChartsData().getProductPricesChart().getConfig());

        mvc.perform(get("/api/graphics/agriculture/product-prices/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andDo(document("product-prices-config",
                        responseFields(
                                fieldWithPath("years").description("Years with product price data"))));
    }

    @Test
    public void getProductPricesChartData() throws Exception {
        ProductPricesChart chart = agricultureData.getAgricultureChartsData().getProductPricesChart();

        given(agricultureChartsService.getProductPricesChartData(any()))
                .willReturn(chart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(ProductPricesChartFilter.class);

        mvc.perform(post("/api/graphics/agriculture/product-prices/data")
                .content(om.writeValueAsBytes(chart.getFilter()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("prices").isNotEmpty())
                .andExpect(jsonPath("previousYearAverages").isNotEmpty())
                .andDo(document("product-prices-data",
                        requestFields(
                                constrainedFields.withPath("year").description("Year"),
                                constrainedFields.withPath("productId").description("<<product,Product>> Id"),
                                constrainedFields.withPath("marketId").description("<<market,Market>> Id")),
                        responseFields(
                                subsectionWithPath("prices")
                                        .description("<<product-price,Product prices>> for the requested filter"),
                                subsectionWithPath("previousYearAverages")
                                        .description("<<average-price,Average prices>> for the previous year")),
                        responseFields(
                                beneathPath("prices").withSubsectionId("productPrice"),
                                fieldWithPath("monthDay").description("Date of the observed price. Format --mm-dd."),
                                fieldWithPath("price").description("Product price"),
                                fieldWithPath("priceTypeId").description("<<price-type,Price Type>> Id")),
                        responseFields(
                                beneathPath("previousYearAverages").withSubsectionId("averagePrice"),
                                subsectionWithPath("average").description("Average price"),
                                subsectionWithPath("priceTypeId").description("<<price-type,Price Type>> Id"))));
    }

    @Test
    public void getProductQuantitiesChartConfig() throws Exception {
        given(agricultureChartsService.getProductQuantitiesChartConfig())
                .willReturn(agricultureData.getAgricultureChartsData().getProductQuantitiesChart().getConfig());

        mvc.perform(get("/api/graphics/agriculture/product-quantities/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("years").isNotEmpty())
                .andDo(document("product-quantities-config",
                        responseFields(
                                fieldWithPath("years").description("Years with product quantity data"))));
    }

    @Test
    public void getProductQuantitiesChartData() throws Exception {
        ProductQuantitiesChart chart = agricultureData.getAgricultureChartsData().getProductQuantitiesChart();

        given(agricultureChartsService.getProductQuantitiesChartData(any()))
                .willReturn(chart.getData());

        ConstrainedFields constrainedFields = new ConstrainedFields(ProductQuantitiesChartFilter.class);

        mvc.perform(post("/api/graphics/agriculture/product-quantities/data")
                .content(om.writeValueAsBytes(chart.getFilter()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("quantities").isNotEmpty())
                .andDo(document("product-quantities-data",
                        requestFields(
                                constrainedFields.withPath("year").description("Year"),
                                constrainedFields.withPath("productTypeId").description("<<product-type,Product Type>> Id"),
                                constrainedFields.withPath("marketId").description("<<market,Market>> Id")),
                        responseFields(
                                subsectionWithPath("quantities")
                                        .description("<<product-quantity,Product quantities>> for the requested filter")),
                        responseFields(
                                beneathPath("quantities").withSubsectionId("productQuantity"),
                                fieldWithPath("monthDay").description("Date of the observed quantity. Format --mm-dd."),
                                fieldWithPath("productId").description("<<product,Product>> Id"),
                                fieldWithPath("quantity").description("Product quantity"))));
    }
}
