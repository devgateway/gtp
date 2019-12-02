package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.dto.MarketPriceDTO;
import org.devgateway.toolkit.persistence.service.MarketPriceService;
import org.devgateway.toolkit.web.rest.controller.filter.MarketFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.MarketPriceFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/marketPrice")
@CrossOrigin
public class MarketPriceController extends AbstractDatasetController<MarketPrice, MarketFilterPagingRequest,
        MarketPriceDTO> {

    private MarketPriceService marketPriceService;

    public MarketPriceController(MarketPriceService marketPriceService) {
        super(marketPriceService);
        this.marketPriceService = marketPriceService;
    }

    @Override
    protected Specification<MarketPrice> getSpecifications(MarketFilterPagingRequest request) {
        MarketPriceFilterState filterState = new MarketPriceFilterState(request);
        return filterState.getSpecification();
    }

    @Override
    protected MarketPriceDTO getDTO(MarketPrice indicator, String lang) {
        return new MarketPriceDTO(indicator, lang);
    }
}
