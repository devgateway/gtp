package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.service.RapidLinkService;
import org.devgateway.toolkit.web.rest.controller.filter.GenericPagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/rapidLink")
@CrossOrigin
public class RapidLinkController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RapidLinkController.class);

    @Autowired
    private RapidLinkService service;

    @CrossOrigin
    @ApiOperation(value = "Get rapid link paginated list.")
    @RequestMapping(value = "/all", method = {POST, GET})
    public Page<RapidLink> getRapidLinkPaginated(@ModelAttribute @Valid final GenericPagingRequest request) {
        LOGGER.info("get rapid link paginated list");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        return service.findAll(pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Get rapid link top 5 list.")
    @RequestMapping(value = "/top5", method = {POST, GET})
    public Iterable<RapidLink> getTop5() {
        LOGGER.info("get rapid link top 5 list");
        return service.findByRapidLinkPositionIdNotNull();
    }
}
