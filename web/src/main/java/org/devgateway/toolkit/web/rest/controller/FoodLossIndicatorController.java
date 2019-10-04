package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
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
@RestController
@RequestMapping(value = "/data/foodLoss")
@CrossOrigin
public class FoodLossIndicatorController extends AbstractDatasetController<FoodLossIndicator,
        FoodLossFilterPagingRequest> {

    public static final String KILOGRAM = "avgKilogram";
    public static final String PERCENTAGE = "avgPercentage";

    public FoodLossIndicatorController(FoodLossIndicatorService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<FoodLossIndicator> getSpecifications(
            FoodLossFilterPagingRequest pagingRequest) {
        FoodLossFilterState filterState = new FoodLossFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = GET)
    public Map<String, Map<String, Integer>> getFoodLossRanges(
            @ModelAttribute @Valid final FoodLossFilterPagingRequest request) {
        Map<String, Map<String, Integer>> ret = new HashMap<>();
        List<FoodLossIndicator> list = datasetService.findAll(getSpecifications(request));
        if (list != null && list.size() > 0) {
            Map<String, Integer> percentageMap = new HashMap<>();
            percentageMap.put(MIN, (int) (Math.floor(Collections.min(list,
                    Comparator.comparing(s -> s.getAvgPercentage())).getAvgPercentage())));
            percentageMap.put(MAX, (int) (Math.ceil(Collections.max(list,
                    Comparator.comparing(s -> s.getAvgPercentage())).getAvgPercentage())));
            ret.put(PERCENTAGE, percentageMap);

            Map<String, Integer> kilogramMap = new HashMap<>();
            kilogramMap.put(MIN, (int) (Math.floor(Collections.min(list,
                    Comparator.comparing(s -> s.getAvgKilograms())).getAvgKilograms())));
            kilogramMap.put(MAX, (int) (Math.ceil(Collections.max(list,
                    Comparator.comparing(s -> s.getAvgKilograms())).getAvgKilograms())));
            ret.put(KILOGRAM, kilogramMap);
        }

        return ret;
    }
}
