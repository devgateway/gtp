package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.service.ConsumptionService;
import org.devgateway.toolkit.web.rest.controller.filter.ConsumptionFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ConsumptionFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/consumption")
@CrossOrigin
public class ConsumptionController extends AbstractDatasetController<Consumption, ConsumptionFilterPagingRequest> {

    public ConsumptionController(ConsumptionService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<Consumption> getSpecifications(ConsumptionFilterPagingRequest pagingRequest) {
        ConsumptionFilterState filterState = new ConsumptionFilterState(pagingRequest);
        return filterState.getSpecification();
    }
}
