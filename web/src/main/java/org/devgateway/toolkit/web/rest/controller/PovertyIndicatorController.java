package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Dump poverty index dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<PovertyIndicator> getAll() {
        return service.findAll();
    }

}
