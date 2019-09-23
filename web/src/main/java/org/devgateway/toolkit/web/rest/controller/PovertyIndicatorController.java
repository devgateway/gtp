package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterState;
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

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/poverty")
@CrossOrigin
public class PovertyIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PovertyIndicatorController.class);

    @Autowired
    private PovertyIndicatorService service;

    @CrossOrigin
    @ApiOperation(value = "Get paginated poverty data")
    @RequestMapping(value = "/all", method = GET)
    public Page<PovertyIndicator> getAll(@ModelAttribute @Valid final PovertyFilterPagingRequest pageRequest) {
        LOGGER.debug("get poverty data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        PovertyFilterState filter = new PovertyFilterState(pageRequest);
        return service.findAll(filter.getSpecification(), pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Dump poverty index dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<PovertyIndicator> getAll() {
        return service.findAll();
    }

}
