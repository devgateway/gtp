package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.dao.WebContent;
import org.devgateway.toolkit.persistence.dto.PartnerDTO;
import org.devgateway.toolkit.persistence.service.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.PartnerService;
import org.devgateway.toolkit.persistence.service.RapidLinkService;
import org.devgateway.toolkit.persistence.service.WebContentService;
import org.devgateway.toolkit.web.rest.controller.filter.GenericPagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data")
@CrossOrigin
public class UIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UIController.class);

    @Autowired
    private RapidLinkService rapidLinkService;

    @Autowired
    private WebContentService contentService;

    @Autowired
    private IndicatorMetadataService indicatorService;

    @Autowired
    private PartnerService partnerService;

    @CrossOrigin
    @ApiOperation(value = "Get all indicator metadata")
    @RequestMapping(value = "/indicatorMetadata/all", method = POST)
    public @ResponseBody
    Page<IndicatorMetadata> getAllIndicatorMetadata(@RequestBody @Valid final GenericPagingRequest request) {
        LOGGER.info("get all content");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        return indicatorService.findAll(pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Get indicator by type id.")
    @RequestMapping(value = "/indicatorMetadata/type/{id}", method = {GET, POST})
    public @ResponseBody
    IndicatorMetadata getIndicatorMetadataById(@PathVariable final int id) {
        LOGGER.info("get indicator by type id: " + id);
        return indicatorService.findByIndicatorType(id);
    }

    @CrossOrigin
    @ApiOperation(value = "Get all web content paginated")
    @RequestMapping(value = "/webContent/all", method = POST)
    public @ResponseBody
    Page<WebContent> getAllContent(@RequestBody @Valid final GenericPagingRequest request) {
        LOGGER.info("get all content");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        return contentService.findAll(pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Get web content by id.")
    @RequestMapping(value = "/webContent/{id}", method = {GET, POST})
    public @ResponseBody
    Optional<WebContent> getContentById(@PathVariable final long id) {
        LOGGER.info("get content by id: " + id);
        return contentService.findById(id);
    }

    @CrossOrigin
    @ApiOperation(value = "Get rapid link paginated list.")
    @RequestMapping(value = "/rapidLink/all", method = POST)
    public @ResponseBody Page<RapidLink> getRapidLinkPaginated(
            @RequestBody @Valid final GenericPagingRequest request) {
        LOGGER.info("get rapid link paginated list");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        return rapidLinkService.findAll(pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Get rapid link top 5 list.")
    @RequestMapping(value = "/rapidLink/top5", method = {GET, POST})
    public @ResponseBody Iterable<RapidLink> getTop5() {
        LOGGER.info("get rapid link top 5 list");
        return rapidLinkService.findByRapidLinkPositionIdNotNull();
    }

    @CrossOrigin
    @ApiOperation(value = "Get partner list.")
    @RequestMapping(value = "/partner/all", method = {GET, POST})
    public @ResponseBody
    List<PartnerDTO> getAllPartners(@RequestBody @Valid final GenericPagingRequest request) {
        LOGGER.info("get partner list");
        return partnerService.findPartnerOrdered(request.getLang());
    }
}
