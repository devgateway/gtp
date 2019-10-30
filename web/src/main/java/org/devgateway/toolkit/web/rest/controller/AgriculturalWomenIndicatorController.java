package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterState;
import org.springframework.cache.annotation.CacheConfig;
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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/agriculturalWomen")
@CrossOrigin
@CacheConfig(cacheNames = "servicesCache")
public class AgriculturalWomenIndicatorController extends AbstractDatasetController<AgriculturalWomenIndicator,
        AgriculturalWomenFilterPagingRequest> {

    public static final String PERCENTAGE = "percentage";

    public AgriculturalWomenIndicatorController(AgriculturalWomenIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<AgriculturalWomenIndicator> getSpecifications(
            AgriculturalWomenFilterPagingRequest pagingRequest) {
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = {POST, GET})
    public Map<String, Map<String, Integer>> getAgriculturalWomenRanges(
            @ModelAttribute @Valid final AgriculturalWomenFilterPagingRequest request) {
        Map<String, Map<String, Integer>> ret = new HashMap<>();
        List<AgriculturalWomenIndicator> list = datasetService.findAll(getSpecifications(request));
        if (list != null && list.size() > 0) {
            Map<String, Integer> map = new HashMap<>();
            map.put(MIN, (int) (Math.floor(Collections.min(list,
                    Comparator.comparing(s -> s.getPercentage())).getPercentage())));
            map.put(MAX, (int) (Math.ceil(Collections.max(list,
                    Comparator.comparing(s -> s.getPercentage())).getPercentage())));
            ret.put(PERCENTAGE, map);
        }

        return ret;
    }

}
