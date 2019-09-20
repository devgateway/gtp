package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/agriculturalWomen")
@CrossOrigin
public class AgriculturalWomenIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgriculturalWomenIndicatorController.class);

    @Autowired
    private AgriculturalWomenIndicatorService service;

    @CrossOrigin
    @ApiOperation(value = "Dump agricultural women dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<AgriculturalWomenIndicator> getAll() {
        return service.findAll();
    }

}
