package org.devgateway.toolkit.web.rest.controller;


import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/poverty")
@CrossOrigin
public class PovertyIndicatorController extends AbstractDatasetController<PovertyIndicator,
        PovertyFilterPagingRequest> {

    public PovertyIndicatorController(PovertyIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<PovertyIndicator> getSpecifications(
            PovertyFilterPagingRequest pagingRequest) {
        PovertyFilterState filterState = new PovertyFilterState(pagingRequest);
        return filterState.getSpecification();
    }

}
