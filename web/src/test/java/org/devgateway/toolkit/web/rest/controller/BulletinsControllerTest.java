package org.devgateway.toolkit.web.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.devgateway.toolkit.persistence.service.GTPService;
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
@WebMvcTest(BulletinsController.class)
public class BulletinsControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private GTPService service;

    private final SampleGTPBulletins sampleGTPBulletins = new SampleGTPBulletins();

    @Test
    public void getGTPBulletins() throws Exception {
        given(service.getGTPMaterials()).willReturn(sampleGTPBulletins.getMaterials());

        mvc.perform(get("/api/gtp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bulletins").isNotEmpty())
                .andExpect(jsonPath("annualReports").isNotEmpty())
                .andDo(document("gtp-bulletins",
                        responseFields(
                                subsectionWithPath("annualReports").description("<<gtp-annual-report,GTP annual report>>"),
                                subsectionWithPath("bulletins").description("<<gtp-bulletin,GTP bulletins>>")),
                        responseFields(
                                beneathPath("bulletins").withSubsectionId("bulletins"),
                                fieldWithPath("id").description("GTP bulletin Id"),
                                fieldWithPath("year").description("Year"),
                                fieldWithPath("month").description("Month"),
                                fieldWithPath("decadal").description("Decadal")),
                        responseFields(
                                beneathPath("annualReports").withSubsectionId("annualReports"),
                                fieldWithPath("id").description("Annual GTP bulletin Id"),
                                fieldWithPath("year").description("Year"))));
    }

    @Test
    public void testGTPBulletinReturned() throws Exception {
        given(service.findBulletin(any())).willReturn(Optional.of(sampleGTPBulletins.getBulletin1()));

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
        given(service.findAnnualReport(any())).willReturn(Optional.of(sampleGTPBulletins.getAnnualReport1()));

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
}
