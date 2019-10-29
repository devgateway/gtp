package org.devgateway.toolkit.web.rest.controller;


import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterState;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Daniel Oliva
 */
@Cacheable
@RestController
@RequestMapping(value = "/data/aoi")
@CrossOrigin
@CacheConfig(cacheNames = "servicesCache")
public class AOIIndicatorController extends AbstractDatasetController<AgricultureOrientationIndexIndicator,
        AOIFilterPagingRequest> {

    public static final String AGE = "age";
    public static final String SCORE = "score";

    public AOIIndicatorController(AOIIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<AgricultureOrientationIndexIndicator> getSpecifications(
            AOIFilterPagingRequest pagingRequest) {
        AOIFilterState filterState = new AOIFilterState(pagingRequest);
        return filterState.getSpecification();
    }

}
