package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/agriculturalWomen")
@CrossOrigin
public class AgriculturalWomenIndicatorController extends AbstractDatasetController<AgriculturalWomenIndicator,
        AgriculturalWomenFilterPagingRequest> {

    public AgriculturalWomenIndicatorController(AgriculturalWomenIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<AgriculturalWomenIndicator> getSpecifications(
            AgriculturalWomenFilterPagingRequest pagingRequest) {
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(pagingRequest);
        return filterState.getSpecification();
    }

}
