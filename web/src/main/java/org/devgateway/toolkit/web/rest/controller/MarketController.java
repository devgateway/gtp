package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/market")
@CrossOrigin
public class MarketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetadataController.class);
    private static final int PAGE_LIMIT_DEFAULT = 300;

    @Autowired
    MarketService marketService;

    @CrossOrigin
    @ApiOperation(value = "Get all market data")
    @RequestMapping(value = "/all", method = GET)
    public Page<Market> getAll(@PageableDefault(page = 0, size = PAGE_LIMIT_DEFAULT, sort = "id")
                              final Pageable pageable) {
        LOGGER.debug("get market data");
        Pageable fixedPage = pageable;
        if (pageable.getPageSize() > PAGE_LIMIT_DEFAULT) {
            fixedPage = PageRequest.of(pageable.getPageNumber(), PAGE_LIMIT_DEFAULT, pageable.getSort());
        }
        return marketService.findAll(fixedPage);
    }
}
