package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/foodLoss")
@CrossOrigin
public class FoodLossIndicatorController extends AbstractDatasetController<FoodLossIndicator,
        FoodLossFilterPagingRequest> {

    public FoodLossIndicatorController(FoodLossIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<FoodLossIndicator> getSpecifications(
            FoodLossFilterPagingRequest pagingRequest) {
        FoodLossFilterState filterState = new FoodLossFilterState(pagingRequest);
        return filterState.getSpecification();
    }
}
