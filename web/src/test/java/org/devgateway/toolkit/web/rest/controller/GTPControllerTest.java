package org.devgateway.toolkit.web.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsFilter;
import org.devgateway.toolkit.persistence.service.GTPService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GTPController.class)
public class GTPControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private GTPService service;

    private final SampleGTPData sampleData = new SampleGTPData();

    @Test
    public void getMaterials() throws Exception {
        given(service.getGTPMaterials()).willReturn(sampleData.getMaterialsData());

        mvc.perform(get("/api/gtp/materials/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("config").isNotEmpty())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("filter").isNotEmpty())
                .andDo(document("gtp-bulletins",
                        responseFields(
                                subsectionWithPath("config").description("<<gtp-materials-config-fields,GTP Materials configuration>>"),
                                subsectionWithPath("data").description("<<gtp-materials-data-fields,GTP Materials data>>"),
                                subsectionWithPath("filter").description("Default <<gtp-materials-data-filter,GTP Materials filter>>")),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                subsectionWithPath("annualReports").description("<<gtp-annual-report,GTP annual report>>"),
                                subsectionWithPath("bulletins").description("<<gtp-bulletin,GTP bulletins>>"))));
    }

    @Test
    public void getGTPMaterialsConfig() throws Exception {
        given(service.getGTPMaterialsConfig()).willReturn(sampleData.getConfig());

        mvc.perform(get("/api/gtp/materials/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("locations").isNotEmpty())
                .andDo(document("gtp-materials-config",
                        responseFields(
                                subsectionWithPath("locations")
                                        .description("<<gtp-materials-config-location-fields,GTP Locations>> with uploaded materials")),
                        responseFields(beneathPath("locations").withSubsectionId("locations"),
                                fieldWithPath("id").optional().type(JsonFieldType.NUMBER)
                                        .description("Location Id (not null = Department, null = National)"),
                                fieldWithPath("name").description("Location name"))
                        ));
    }

    @Test
    public void getMaterialsFiltered() throws Exception {
        given(service.getGTPMaterialsFiltered(any())).willReturn(sampleData.getMaterials());

        ConstrainedFields constrainedFields = new ConstrainedFields(GTPMaterialsFilter.class);
        om.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        mvc.perform(post("/api/gtp/materials/data")
                .content(om.writeValueAsBytes(sampleData.getFilter()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bulletins").isNotEmpty())
                .andExpect(jsonPath("annualReports").isNotEmpty())
                .andDo(document("gtp-materials-data",
                        requestFields(
                                constrainedFields.withPath("locationId").optional().type(JsonFieldType.NUMBER)
                                        .description("<<gtp-materials-config-location-fields,Location>> Id")),
                        responseFields(
                                subsectionWithPath("annualReports").description("<<gtp-annual-report,GTP annual report>>"),
                                subsectionWithPath("bulletins").description("<<gtp-bulletin,GTP bulletins>>")),
                        responseFields(
                                beneathPath("bulletins").withSubsectionId("bulletins"),
                                fieldWithPath("id").description("GTP bulletin Id"),
                                fieldWithPath("year").description("Year"),
                                fieldWithPath("month").description("Month"),
                                fieldWithPath("decadal").description("Decadal"),
                                fieldWithPath("locationId").optional().type(JsonFieldType.NUMBER).description("Location Id")),
                        responseFields(
                                beneathPath("annualReports").withSubsectionId("annualReports"),
                                fieldWithPath("id").description("Annual GTP bulletin Id"),
                                fieldWithPath("year").description("Year"),
                                fieldWithPath("locationId").optional().type(JsonFieldType.NUMBER).description("Location Id"))));

    }

    @Test
    public void testGTPBulletinReturned() throws Exception {
        given(service.findBulletin(any())).willReturn(Optional.of(sampleData.getBulletin1()));

        mvc.perform(get("/api/gtp/bulletin?id={id}", 1L))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Disposition"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andDo(document("gtp-bulletin-returned",
                        requestParameters(parameterWithName("id").description("GTP bulletin Id"))));
    }

    @Test
    public void testGTPBulletinNotFound() throws Exception {
        given(service.findBulletin(any())).willReturn(Optional.empty());

        mvc.perform(get("/api/gtp/bulletin?id={id}", 998L))
                .andExpect(status().isNotFound())
                .andDo(document("gtp-bulletin-not-found"));
    }

    @Test
    public void testAnnualReportReturned() throws Exception {
        given(service.findAnnualReport(any())).willReturn(Optional.of(sampleData.getAnnualReport1()));

        mvc.perform(get("/api/gtp/annual-report?id={id}", 1L))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Disposition"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andDo(document("gtp-annual-report-returned",
                        requestParameters(parameterWithName("id").description("GTP annual report Id"))));
    }

    @Test
    public void testAnnualReportNotFound() throws Exception {
        given(service.findAnnualReport(any())).willReturn(Optional.empty());

        mvc.perform(get("/api/gtp/annual-report?id={id}", 998L))
                .andExpect(status().isNotFound())
                .andDo(document("gtp-annual-report-not-found"));
    }

    @Test
    public void getMembers() throws Exception {
        given(service.getGTPMembers()).willReturn(sampleData.getMembers());

        mvc.perform(get("/api/gtp/members"))
                .andExpect(status().isOk())
                .andDo(document("gtp-members",
                        responseFields(
                                subsectionWithPath("[].id").description("GTP Member Id"),
                                subsectionWithPath("[].name").description("Name"),
                                subsectionWithPath("[].description").description("Description"),
                                subsectionWithPath("[].url").description("Website URL"))));
    }

    @Test
    public void getMemberLogo() throws Exception {
        given(service.getMember(any())).willReturn(Optional.of(sampleData.getAnacim()));

        mvc.perform(get("/api/gtp/member/logo?id={id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("gtp-member-logo-returned",
                        requestParameters(parameterWithName("id").description("GTP Member Id"))));
    }
}
