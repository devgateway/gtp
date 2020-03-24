package org.devgateway.toolkit.web.rest.controller.ipar;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dto.ipar.AgriculturalWomenDTO;
import org.devgateway.toolkit.persistence.dto.ipar.AgriculturalWomenSummary;
import org.devgateway.toolkit.persistence.repository.ipar.SummaryIndicatorRepository;
import org.devgateway.toolkit.persistence.service.ipar.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.category.AgriculturalWomenGroupService;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AgriculturalWomenFilterState;
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
@RequestMapping(value = "/data/agriculturalWomen")
@CrossOrigin
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "servicesCache")
@Cacheable
public class AgriculturalWomenIndicatorController extends AbstractDatasetController<AgriculturalWomenIndicator,
        AgriculturalWomenFilterPagingRequest, AgriculturalWomenDTO> {

    public static final String PERCENTAGE = "percentage";
    public static final int AGE_GROUP = 1;
    public static final int METHOD_OF_ENFORCEMENT = 2;
    public static final int DISTRIBUTION_AREAS_PARCELS = 3;
    public static final int DISTRIBUTION_ZONES_UTILIZED_ZONES = 4;

    @Autowired
    private SummaryIndicatorRepository summaryIndicatorRepository;

    private final List<AgriculturalWomenGroup> groups;

    public AgriculturalWomenIndicatorController(AgriculturalWomenIndicatorService indicatorService,
                                                AgriculturalWomenGroupService awGroupService) {
        super(indicatorService);
        groups = awGroupService.findAll();
    }

    @Override
    protected Specification<AgriculturalWomenIndicator> getSpecifications(
            AgriculturalWomenFilterPagingRequest pagingRequest) {
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @Override
    protected AgriculturalWomenDTO getDTO(AgriculturalWomenIndicator indicator, String lang) {
        return new AgriculturalWomenDTO(indicator, lang);
    }

    @CrossOrigin
    @ApiOperation(value = "Get ranges")
    @RequestMapping(value = "/range", method = POST)
    public @ResponseBody Map<String, Map<String, Integer>> getAgriculturalWomenRanges(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest request) {
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

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women summary data")
    @RequestMapping(value = "/summary", method = POST)
    public @ResponseBody List<AgriculturalWomenSummary> getSummaryIndicatorWomen(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest req) {
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(req);
        return summaryIndicatorRepository.getAgriculturalWomenIndicator(filterState.getSpecification());
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women summary data by age group")
    @RequestMapping(value = "/summary/byAgeGroup", method = POST)
    public @ResponseBody List<AgriculturalWomenSummary> getSummaryIndicatorWomenByAgeGroup(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest req) {
        return getSummaryByGroup(req, AGE_GROUP);
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women summary data by method of enforcement")
    @RequestMapping(value = "/summary/byMethodOfEnforcement", method = POST)
    public @ResponseBody List<AgriculturalWomenSummary> getSummaryIndicatorWomenByMethodOfEnforcement(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest req) {
        return getSummaryByGroup(req, METHOD_OF_ENFORCEMENT);
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women summary data by distribution areas/parcels")
    @RequestMapping(value = "/summary/byAreas", method = POST)
    public @ResponseBody List<AgriculturalWomenSummary> getSummaryIndicatorWomenByAreas(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest req) {
        return getSummaryByGroup(req, DISTRIBUTION_AREAS_PARCELS);
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women summary data by distribution zones/utilized zones")
    @RequestMapping(value = "/summary/byZones", method = POST)
    public @ResponseBody List<AgriculturalWomenSummary> getSummaryIndicatorWomenByZones(
            @RequestBody(required = false) @Valid final AgriculturalWomenFilterPagingRequest req) {
        return getSummaryByGroup(req, DISTRIBUTION_ZONES_UTILIZED_ZONES);
    }

    private List<AgriculturalWomenSummary> getSummaryByGroup(AgriculturalWomenFilterPagingRequest req,
                                                             final int type) {
       List<Integer> idList = groups.stream()
                .filter(g -> g.getType().equals(type))
                .map(g -> new Integer(g.getId().intValue()))
                .collect(Collectors.toList());
        if (req == null) {
            req = new AgriculturalWomenFilterPagingRequest();
        }
        req.setAwGroup(new TreeSet<>(idList));

        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(req);
        return summaryIndicatorRepository.getAgriculturalWomenIndicator(filterState.getSpecification());
    }

}
