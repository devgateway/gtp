package org.devgateway.toolkit.web.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.devgateway.toolkit.persistence.dto.CommonConfig;
import org.devgateway.toolkit.persistence.service.charts.ChartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CommonConfigController.class)
public class CommonConfigControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private ChartService chartService;

    @Test
    public void getCommonConfig() throws Exception {
        SampleCommonData commonData = new SampleCommonData();

        CommonConfig config = new CommonConfig(commonData.getDepartments(), commonData.getRegions(),
                commonData.getZones());
        given(chartService.getCommonConfig()).willReturn(config);

        mvc.perform(get("/api/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("zones").isNotEmpty())
                .andExpect(jsonPath("regions").isNotEmpty())
                .andExpect(jsonPath("departments").isNotEmpty())
                .andDo(document("common-config",
                        responseFields(
                                subsectionWithPath("zones").description("<<zone,Zones>>"),
                                subsectionWithPath("regions").description("<<region,Regions>>"),
                                subsectionWithPath("departments").description("<<department,Departments>>")),
                        responseFields(
                                beneathPath("zones"),
                                fieldWithPath("id").description("Zone Id"),
                                fieldWithPath("name").description("Name")),
                        responseFields(
                                beneathPath("regions"),
                                fieldWithPath("id").description("Region Id"),
                                fieldWithPath("name").description("Name"),
                                fieldWithPath("code").description("ISO 3166-2 Code"),
                                fieldWithPath("zoneId").description("Zone Id")),
                        responseFields(
                                beneathPath("departments"),
                                fieldWithPath("id").description("Department Id"),
                                fieldWithPath("name").description("Name"),
                                fieldWithPath("code").description("ISO 3166-2 Code"),
                                fieldWithPath("regionId").description("Region Id"))));
    }
}
