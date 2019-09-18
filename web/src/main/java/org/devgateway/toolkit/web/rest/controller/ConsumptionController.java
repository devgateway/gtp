package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.service.ConsumptionService;
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
@RequestMapping(value = "/data/consumption")
@CrossOrigin
public class ConsumptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionController.class);

    @Autowired
    private ConsumptionService consumptionService;


    @CrossOrigin
    @ApiOperation(value = "Dump consumption data")
    @RequestMapping(value = "/dump", method = GET)
    public List<Consumption> getAll() {
        LOGGER.debug("get consumption data");
        return consumptionService.findAll();
    }
}
