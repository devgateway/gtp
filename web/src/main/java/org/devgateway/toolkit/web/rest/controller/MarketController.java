package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.service.MarketService;
import org.devgateway.toolkit.web.rest.controller.filter.MarketFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.MarketFilterState;
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

@RestController
@RequestMapping(value = "/market")
@CrossOrigin
public class MarketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetadataController.class);

    @Autowired
    MarketService marketService;

    @CrossOrigin
    @ApiOperation(value = "Get all market data")
    @RequestMapping(value = "/all", method = GET)
    public Page<Market> getAll(@ModelAttribute @Valid final MarketFilterPagingRequest pageRequest) {
        LOGGER.debug("get market data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        MarketFilterState filter = new MarketFilterState(pageRequest);
        return marketService.findAll(filter.getSpecification(), pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Dump market dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<Market> getAll() {
        return marketService.findAll();
    }
}
