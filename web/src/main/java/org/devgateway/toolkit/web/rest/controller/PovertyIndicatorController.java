package org.devgateway.toolkit.web.rest.controller;


import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterState;
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

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Created by Daniel Oliva
 */
@Cacheable
@RestController
@RequestMapping(value = "/data/poverty")
@CrossOrigin
@CacheConfig(cacheNames = "servicesCache")
public class PovertyIndicatorController extends AbstractDatasetController<PovertyIndicator,
        PovertyFilterPagingRequest> {

    public static final String AGE = "age";
    public static final String SCORE = "score";

    public PovertyIndicatorController(PovertyIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<PovertyIndicator> getSpecifications(
            PovertyFilterPagingRequest pagingRequest) {
        PovertyFilterState filterState = new PovertyFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = GET)
    public Map<String, Map<String, Integer>> getPovertyRanges(
            @ModelAttribute @Valid final PovertyFilterPagingRequest request) {
        Map<String, Map<String, Integer>> ret = new HashMap<>();
        List<PovertyIndicator> povertyList = datasetService.findAll(getSpecifications(request));
        if (povertyList != null && povertyList.size() > 0) {
            Map<String, Integer> ageMap = new HashMap<>();
            ageMap.put(MIN, Collections.min(povertyList,
                    Comparator.comparing(s -> s.getAge())).getAge());
            ageMap.put(MAX, Collections.max(povertyList,
                    Comparator.comparing(s -> s.getAge())).getAge());
            ret.put(AGE, ageMap);

            Map<String, Integer> scoreMap = new HashMap<>();
            scoreMap.put(MIN, (int) (Math.floor(Collections.min(povertyList,
                    Comparator.comparing(s -> s.getPovertyScore())).getPovertyScore())));
            scoreMap.put(MAX, (int) (Math.ceil(Collections.max(povertyList,
                    Comparator.comparing(s -> s.getPovertyScore())).getPovertyScore())));
            ret.put(SCORE, scoreMap);
        }

        return ret;
    }

}
