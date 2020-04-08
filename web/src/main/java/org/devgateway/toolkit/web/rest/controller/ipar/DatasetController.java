package org.devgateway.toolkit.web.rest.controller.ipar;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.dao.ipar.categories.DatasetType;
import org.devgateway.toolkit.persistence.dto.ipar.AgriculturalContentDTO;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.dto.ipar.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.dto.ipar.NationalIndicatorDTO;
import org.devgateway.toolkit.persistence.repository.ipar.category.DatasetTypeRepository;
import org.devgateway.toolkit.persistence.service.DatasetFinderService;
import org.devgateway.toolkit.persistence.service.ipar.AgriculturalContentService;
import org.devgateway.toolkit.persistence.service.ipar.DepartmentIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.GisSettingsService;
import org.devgateway.toolkit.persistence.service.ipar.MicrodataLinkService;
import org.devgateway.toolkit.persistence.service.ipar.NationalIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.RegionIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.GenericPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.DatasetFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.DatasetFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.MicrodataLinkFilterState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

 // @RestController
@RequestMapping(value = "/data")
@CrossOrigin
public class DatasetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetController.class);
    public static final int DATASOURCE_PAGE_SIZE = 20;
    public static final int DATASET_PAGE_SIZE = 10;
    public static final int AGRICULTURAL_CONTENT_PAGE_SIZE = 6;

    @Autowired
    @Qualifier(value = "datasetService")
    private DatasetFinderService datasetService;

    @Autowired
    private MicrodataLinkService microdataLinkService;

    @Autowired
    private RegionIndicatorService regionIndicatorService;

    @Autowired
    private DepartmentIndicatorService departmentIndicatorService;

    @Autowired
    private AgriculturalContentService agriculturalContentService;

    @Autowired
    private DatasetTypeRepository datasetTypeRepository;

    @Autowired
    private NationalIndicatorService nationalIndicatorService;

    @Autowired
    private GisSettingsService gisSettingsService;

    @CrossOrigin
    @ApiOperation(value = "Get all datasets metadata")
    @RequestMapping(value = "/dataset/all", method = POST)
    public @ResponseBody
    Page<DatasetDTO> getAllDatasets(@RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all datasets");
        int pageSize = request.getPageSize() != GenericPagingRequest.DEFAULT_PAGE_SIZE
                ? request.getPageSize() : DATASET_PAGE_SIZE;
        Pageable pageable = PageRequest.of(request.getPageNumber(), pageSize);
        List<DatasetType> datasetTypes = datasetTypeRepository.findAll();
        DatasetFilterState filter = new DatasetFilterState(request, datasetTypes);

        List<DatasetDTO> dtoList = datasetService.findAllDTO(filter.getSpecification(), request.getLang());
        if (request.getSortBy().equalsIgnoreCase("type")) {
            if (request.getSortDir().equalsIgnoreCase("asc")) {
                dtoList = dtoList.stream().sorted(Comparator.comparing(DatasetDTO::getType))
                        .collect(Collectors.toList());
            } else {
                dtoList = dtoList.stream().sorted(Comparator.comparing(DatasetDTO::getType).reversed())
                        .collect(Collectors.toList());
            }
        }
        int start = request.getPageNumber() * pageSize;
        int end = start + pageSize > dtoList.size() ? dtoList.size() : start + pageSize;
        List<DatasetDTO> ret = dtoList.subList(start, end);

        return new PageImpl(ret, pageable, dtoList.size());
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
    @ApiOperation(value = "Get all national indicators metadata")
    @RequestMapping(value = "/nationalIndicator/all", method = POST)
    public @ResponseBody
    List<NationalIndicatorDTO> getAllNationalIndicator(@RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all national indicators");
        GisSettings gisSettings = gisSettingsService.findAll().get(0);
        return nationalIndicatorService.findAll().stream()
                .filter(n -> n.isApproved())
                .map(n -> new NationalIndicatorDTO(n, request.getLang(), gisSettings))
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all GIS region indicator metadata")
    @RequestMapping(value = "/gisIndicator/region/all", method = POST)
    public @ResponseBody List<GisIndicatorDTO> getAllGisRegionIndicators(
            @RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all GIS indicators");
        return regionIndicatorService.findGisRegionIndicators(request.getLang());
    }

    @CrossOrigin
    @ApiOperation(value = "Get all GIS department indicator metadata")
    @RequestMapping(value = "/gisIndicator/department/all", method = POST)
    public @ResponseBody List<GisIndicatorDTO> getAllGisDepartmentIndicators(
            @RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all GIS indicators");
        return departmentIndicatorService.findGisDepartmentIndicators(request.getLang());
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
