package org.devgateway.toolkit.web.rest.controller;


import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterState;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


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

    public static final String BUDGETED = "budgetedExpenditures";
    public static final String DISBURSED = "disbursedExpenditures";
    public static final String SUBSIDIES = "subsidies";

    public AOIIndicatorController(AOIIndicatorService datasetService) {
        super(datasetService);
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = {POST, GET})
    public Map<String, Map<String, Double>> getAOIRanges(
            @ModelAttribute @Valid final AOIFilterPagingRequest request) {
        Map<String, Map<String, Double>> ret = new HashMap<>();
        List<AgricultureOrientationIndexIndicator> list = datasetService.findAll(getSpecifications(request));
        if (list != null && list.size() > 0) {
            Map<String, Double> budgetedExpenditures = new HashMap<>();
            List<AgricultureOrientationIndexIndicator> sublist = list.stream()
                    .filter(s -> s.getBudgetedExpenditures() != null).collect(Collectors.toList());
            budgetedExpenditures.put(MIN, Collections.min(sublist,
                    Comparator.comparingDouble(s -> s.getBudgetedExpenditures())).getBudgetedExpenditures());
            budgetedExpenditures.put(MAX, Collections.max(sublist,
                    Comparator.comparingDouble(s -> s.getBudgetedExpenditures())).getBudgetedExpenditures());
            ret.put(BUDGETED, budgetedExpenditures);


            sublist = list.stream()
                    .filter(s -> s.getDisbursedExpenditures() != null).collect(Collectors.toList());
            Map<String, Double> disbursedExpenditures = new HashMap<>();
            disbursedExpenditures.put(MIN, Collections.min(sublist,
                    Comparator.comparingDouble(s -> s.getDisbursedExpenditures())).getDisbursedExpenditures());
            disbursedExpenditures.put(MAX, Collections.max(sublist,
                    Comparator.comparingDouble(s -> s.getDisbursedExpenditures())).getDisbursedExpenditures());
            ret.put(DISBURSED, disbursedExpenditures);

            sublist = list.stream()
                    .filter(s -> s.getSubsidies() != null).collect(Collectors.toList());
            Map<String, Double> subsidies = new HashMap<>();
            subsidies.put(MIN, Collections.min(sublist,
                    Comparator.comparingDouble(s -> s.getSubsidies())).getSubsidies());
            subsidies.put(MAX, Collections.max(sublist,
                    Comparator.comparingDouble(s -> s.getSubsidies())).getSubsidies());
            ret.put(SUBSIDIES, subsidies);
        }

        return ret;
    }

    @Override
    protected Specification<AgricultureOrientationIndexIndicator> getSpecifications(
            AOIFilterPagingRequest pagingRequest) {
        AOIFilterState filterState = new AOIFilterState(pagingRequest);
        return filterState.getSpecification();
    }

}
