package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
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
@RequestMapping(value = "/data/foodLoss")
@CrossOrigin
public class FoodLossIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodLossIndicatorController.class);

    @Autowired
    private FoodLossIndicatorService service;

    @CrossOrigin
    @ApiOperation(value = "Dump agricutural women dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<FoodLossIndicator> getAll() {
        return service.findAll();
    }

}
