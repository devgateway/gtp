package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dto.AgriculturalContentDTO;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.dto.RegionIndicatorDTO;
import org.devgateway.toolkit.persistence.service.AgriculturalContentService;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.devgateway.toolkit.persistence.service.MicrodataLinkService;
import org.devgateway.toolkit.persistence.service.RegionIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.DatasetFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.DatasetFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.GenericPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.MicrodataLinkFilterState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/data")
@CrossOrigin
public class DatasetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetController.class);
    public static final int DATASOURCE_PAGE_SIZE = 20;
    public static final int DATASET_PAGE_SIZE = 10;
    public static final int AGRICULTURAL_CONTENT_PAGE_SIZE = 6;

    @Autowired
    @Qualifier(value = "datasetService")
    private DatasetService datasetService;

    @Autowired
    private MicrodataLinkService microdataLinkService;

    @Autowired
    private RegionIndicatorService regionIndicatorService;

    @Autowired
    private AgriculturalContentService agriculturalContentService;

    @CrossOrigin
    @ApiOperation(value = "Get all datasets metadata")
    @RequestMapping(value = "/dataset/all", method = POST)
    public @ResponseBody
    Page<DatasetDTO> getAllDatasets(@RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all datasets");
        int pageSize = request.getPageSize() != GenericPagingRequest.DEFAULT_PAGE_SIZE
                ? request.getPageSize() : DATASET_PAGE_SIZE;
        Pageable pageable = PageRequest.of(request.getPageNumber(), pageSize);
        DatasetFilterState filter = new DatasetFilterState(request);
        return datasetService.findAllDTO(filter.getSpecification(), pageable, request.getLang());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all datasources metadata")
    @RequestMapping(value = "/datasources/all", method = POST)
    public @ResponseBody
    Page<DatasetDTO> getAllDatasources(@RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all datasources");
        int pageSize = request.getPageSize() != GenericPagingRequest.DEFAULT_PAGE_SIZE
                ? request.getPageSize() : DATASOURCE_PAGE_SIZE;
        Pageable pageable = PageRequest.of(request.getPageNumber(), pageSize);
        MicrodataLinkFilterState filter = new MicrodataLinkFilterState(request);
        return microdataLinkService.findAllDTO(filter.getSpecification(), pageable, request.getLang());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all GIS indicators metadata")
    @RequestMapping(value = "/gisIndicator/all", method = POST)
    public @ResponseBody List<RegionIndicatorDTO> getAllGisIndicator(
            @RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all GIS indicators");
        return regionIndicatorService.findGisIndicatorAndPovertyIndicator(request.getLang());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all agricultural content metadata")
    @RequestMapping(value = "/agriculturalContent/all", method = POST)
    public @ResponseBody List<AgriculturalContentDTO> getAllAgriculturalContent(
            @RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all agricultural content");
        return agriculturalContentService.findPublishedContent(request.getLang());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all agricultural content metadata by type")
    @RequestMapping(value = "/agriculturalContent/type/{id}", method = POST)
    public @ResponseBody Page<AgriculturalContentDTO> getAllAgriculturalContentByType(
            @RequestBody @Valid final DatasetFilterPagingRequest request, @PathVariable final int id) {
        LOGGER.info("get all agricultural content");
        Pageable pageable = PageRequest.of(request.getPageNumber(), AGRICULTURAL_CONTENT_PAGE_SIZE);
        return agriculturalContentService.findByContentType(request.getLang(), id, pageable);
    }
}
