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

import org.devgateway.toolkit.persistence.service.menu.CNSCHeaderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Nadejda Mandrescu
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class AppControllerTest extends AbstractDocumentedControllerTest {

    @MockBean
    private CNSCHeaderService cnscHeaderService;

    private SampleCNSCHeader sampleCNSCHeader;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        sampleCNSCHeader = new SampleCNSCHeader();
    }
    @Test
    public void getCNSCHeader() throws Exception {
        given(cnscHeaderService.get())
                .willReturn(sampleCNSCHeader.getCNSCHeader());

        mvc.perform(get("/api/app/cnsc-header"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("menu").isNotEmpty())
                .andDo(document("get-cnsc-header",
                        responseFields(
                                fieldWithPath("id").description("CNSC Header Id"),
                                fieldWithPath("searchUrl").description("Search URL prefix"),
                                fieldWithPath("isSearchUrlEnabled").description("Is search URL enabled"),
                                subsectionWithPath("menu")
                                        .description("<<cnsc-menu,CNSC Menu>>")),
                        responseFields(
                                beneathPath("menu").withSubsectionId("menu"),
                                fieldWithPath("id").description("Menu entry id"),
                                fieldWithPath("name").description("Menu entry internal name"),
                                fieldWithPath("label").description("Menu entry label"),
                                subsectionWithPath("items")
                                        .description("<<menu-item,Menu Entries>>")),
                        responseFields(
                                beneathPath("menu.items").withSubsectionId("menuItem"),
                                fieldWithPath("id").description("Menu entry id"),
                                fieldWithPath("name").description("Menu entry id").optional(),
                                fieldWithPath("label").description("Menu entry id"),
                                fieldWithPath("index").description("Menu entry order index"),
                                fieldWithPath("url").description("Menu leaf entry url").optional(),
                                subsectionWithPath("items").optional().ignored()
                                        .description("<<menu-item,Menu Entries>>"))));
    }

}
