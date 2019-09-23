package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.service.MarketPriceService;
import org.devgateway.toolkit.web.rest.controller.filter.MarketFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.MarketPriceFilterState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/marketPrice")
@CrossOrigin
public class MarketPriceController extends AbstractDatasetController<MarketPrice> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketPriceController.class);

    private MarketPriceService marketPriceService;

    public MarketPriceController(MarketPriceService marketPriceService) {
        super(marketPriceService);
        this.marketPriceService = marketPriceService;
    }

    @CrossOrigin
    @ApiOperation(value = "Get paginated market price data")
    @RequestMapping(value = "/all", method = GET)
    public Page<MarketPrice> getAll(@ModelAttribute @Valid final MarketFilterPagingRequest pageRequest) {
        LOGGER.debug("get market data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        MarketPriceFilterState filter = new MarketPriceFilterState(pageRequest);
        return marketPriceService.findAll(filter.getSpecification(), pageable);
    }
}
