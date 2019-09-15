package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.service.ProductionService;
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
@RequestMapping(value = "/production")
@CrossOrigin
public class ProductionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionController.class);

    @Autowired
    private ProductionService productionService;


    @CrossOrigin
    @ApiOperation(value = "Dump production data")
    @RequestMapping(value = "/dump", method = GET)
    public List<Production> getAll() {
        LOGGER.debug("get production data");
        return productionService.findAll();
    }
}
