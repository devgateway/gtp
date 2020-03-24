package org.devgateway.toolkit.web.rest.controller.ipar;


import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndexType;
import org.devgateway.toolkit.persistence.dto.ipar.AOISummary;
import org.devgateway.toolkit.persistence.dto.ipar.AgricultureOrientationIndexDTO;
import org.devgateway.toolkit.persistence.repository.ipar.SummaryIndicatorRepository;
import org.devgateway.toolkit.persistence.service.ipar.AOIIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.category.IndexTypeService;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AOIFilterState;
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
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by Daniel Oliva
 */
 // @RestController
@RequestMapping(value = "/data/agOrientation")
@CrossOrigin
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "servicesCache")
@Cacheable
public class AOIIndicatorController extends AbstractDatasetController<AgricultureOrientationIndexIndicator,
        AOIFilterPagingRequest, AgricultureOrientationIndexDTO> {

    public static final String BUDGETED = "budgetedExpenditures";
    public static final String DISBURSED = "disbursedExpenditures";
    public static final String SUBSIDIES = "subsidies";
    public static final int TOTAL_PUBLIC_BUDGET_TYPE_ID = 1;
    public static final int SUBSIDIES_TYPE_ID = 2;
    public static final int RATIOS_TYPE_ID = 3;

    @Autowired
    private SummaryIndicatorRepository summaryIndicatorRepository;

    private final List<IndexType> indexTypes;

    public AOIIndicatorController(AOIIndicatorService datasetService, IndexTypeService indexTypeService) {
        super(datasetService);
        indexTypes = indexTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = POST)
    public @ResponseBody Map<String, Map<String, Double>> getAOIRanges(
            @RequestBody(required = false) @Valid final AOIFilterPagingRequest request) {
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

    @CrossOrigin
    @ApiOperation(value = "Get agriculture orientation index data")
    @RequestMapping(value = "/summary", method = POST)
    public @ResponseBody List<AOISummary> getSummaryIndicatorAOI(
            @RequestBody(required = false) @Valid final AOIFilterPagingRequest req) {
        AOIFilterState filterState = new AOIFilterState(req);
        return summaryIndicatorRepository.getAOIIndicator(filterState.getSpecification());
    }

    @CrossOrigin
    @ApiOperation(value = "Get agriculture orientation index data by total budget")
    @RequestMapping(value = "/summary/totalBudget", method = POST)
    public @ResponseBody List<AOISummary> getSummaryIndicatorAOIByTotalBudget(
            @RequestBody(required = false) @Valid final AOIFilterPagingRequest req) {
        return getSummaryByIndexType(req, TOTAL_PUBLIC_BUDGET_TYPE_ID);
    }

    @CrossOrigin
    @ApiOperation(value = "Get agriculture orientation index data by subsidies")
    @RequestMapping(value = "/summary/subsidies", method = POST)
    public @ResponseBody List<AOISummary> getSummaryIndicatorAOIBySubsidies(
            @RequestBody(required = false) @Valid final AOIFilterPagingRequest req) {
        return getSummaryByIndexType(req, SUBSIDIES_TYPE_ID);
    }

    @CrossOrigin
    @ApiOperation(value = "Get agriculture orientation index data by ratios")
    @RequestMapping(value = "/summary/ratios", method = POST)
    public @ResponseBody List<AOISummary> getSummaryIndicatorAOIByRatios(
            @RequestBody(required = false) @Valid final AOIFilterPagingRequest req) {
        return getSummaryByIndexType(req, RATIOS_TYPE_ID);
    }

    @Override
    protected Specification<AgricultureOrientationIndexIndicator> getSpecifications(
            AOIFilterPagingRequest pagingRequest) {
        AOIFilterState filterState = new AOIFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @Override
    protected AgricultureOrientationIndexDTO getDTO(AgricultureOrientationIndexIndicator indicator, String lang) {
        return new AgricultureOrientationIndexDTO(indicator, lang);
    }

    private List<AOISummary> getSummaryByIndexType(AOIFilterPagingRequest req,
                                                             final int type) {
        List<Integer> idList = indexTypes.stream()
                .filter(g -> g.getType().equals(type))
                .map(g -> new Integer(g.getId().intValue()))
                .collect(Collectors.toList());
        if (req == null) {
            req = new AOIFilterPagingRequest();
        }
        if (req.getIndexType() == null || req.getIndexType().isEmpty()) {
            req.setIndexType(new TreeSet<>(idList));
        }

        AOIFilterState filterState = new AOIFilterState(req);
        return summaryIndicatorRepository.getAOIIndicator(filterState.getSpecification());
    }

}
