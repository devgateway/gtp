package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/market")
@CrossOrigin
public class MarketController {

    @Autowired
    MarketService marketService;

    @CrossOrigin
    @ApiOperation(value = "Get all market data")
    @RequestMapping(value = "/all", method = GET)
    public List<Market> getAll() {
        return marketService.findAll();
    }
}
