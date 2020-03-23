package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.ipar.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dto.ipar.FoodLossDTO;
import org.devgateway.toolkit.persistence.dto.ipar.FoodLossSummary;
import org.devgateway.toolkit.persistence.repository.SummaryIndicatorRepository;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/foodLoss")
@CrossOrigin
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "servicesCache")
@Cacheable
public class FoodLossIndicatorController extends AbstractDatasetController<FoodLossIndicator,
        FoodLossFilterPagingRequest, FoodLossDTO> {

    public static final String KILOGRAM = "avgKilogram";
    public static final String PERCENTAGE = "avgPercentage";

    @Autowired
    private SummaryIndicatorRepository summaryIndicatorRepository;

    public FoodLossIndicatorController(FoodLossIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<FoodLossIndicator> getSpecifications(
            FoodLossFilterPagingRequest pagingRequest) {
        FoodLossFilterState filterState = new FoodLossFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @Override
    protected FoodLossDTO getDTO(FoodLossIndicator indicator, String lang) {
        return new FoodLossDTO(indicator, lang);
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = POST)
    public @ResponseBody Map<String, Map<String, Double>> getFoodLossRanges(
            @RequestBody(required = false) @Valid final FoodLossFilterPagingRequest request) {
        Map<String, Map<String, Double>> ret = new HashMap<>();
        List<FoodLossIndicator> list = datasetService.findAll(getSpecifications(request));
        if (list != null && list.size() > 0) {
            Map<String, Double> percentageMap = new HashMap<>();
            percentageMap.put(MIN, Collections.min(list,
                    Comparator.comparingDouble(s -> s.getAvgPercentage())).getAvgPercentage());
            percentageMap.put(MAX, Collections.max(list,
                    Comparator.comparingDouble(s -> s.getAvgPercentage())).getAvgPercentage());
            ret.put(PERCENTAGE, percentageMap);

            Map<String, Double> kilogramMap = new HashMap<>();
            kilogramMap.put(MIN, Collections.min(list,
                    Comparator.comparingDouble(s -> s.getAvgKilograms())).getAvgKilograms());
            kilogramMap.put(MAX, Collections.max(list,
                    Comparator.comparingDouble(s -> s.getAvgKilograms())).getAvgKilograms());
            ret.put(KILOGRAM, kilogramMap);
        }

        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get food loss summary data")
    @RequestMapping(value = "/summary", method = POST)
    public @ResponseBody List<FoodLossSummary> getSummaryIndicatorWomen(
            @RequestBody(required = false) @Valid final FoodLossFilterPagingRequest req) {
        FoodLossFilterState filterState = new FoodLossFilterState(req);
        return summaryIndicatorRepository.getFoodLossIndicator(filterState.getSpecification());
    }
}
