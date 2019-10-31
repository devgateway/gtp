package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.DefaultFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/indicator")
@CrossOrigin
@CacheConfig(cacheNames = "servicesCache")
public class IndicatorController {

    public static final String ACCUM = "accum";
    public static final String COUNT = "count";
    public static final String YEAR = "year";
    public static final String VALUE = "value";

    @Autowired
    private PovertyIndicatorService povertyService;

    @Autowired
    private FoodLossIndicatorService foodService;

    @Autowired
    private AgriculturalWomenIndicatorService womenService;

    @Autowired
    private AOIIndicatorService aoiService;

    @CrossOrigin
    @ApiOperation(value = "Get summary data")
    @RequestMapping(method = {POST, GET})
    public Map<String, IndicatorData> getIndicatorData(@ModelAttribute @Valid final DefaultFilterPagingRequest req) {
        Map<String, IndicatorData> ret = new HashMap<>();
        ret.put("poverty", getIndicatorPoverty(new PovertyFilterPagingRequest(req)));
        ret.put("foodLoss", getIndicatorFoodLoss(new FoodLossFilterPagingRequest(req)));
        ret.put("agriculturalWomen", getIndicatorAgriculturalWomen(new AgriculturalWomenFilterPagingRequest(req)));
        ret.put("aoi", getIndicatorAOI(new AOIFilterPagingRequest(req)));
        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get poverty summary data")
    @RequestMapping(value = "/poverty", method = {POST, GET})
    public IndicatorData getIndicatorPoverty(@ModelAttribute @Valid final PovertyFilterPagingRequest req) {
        PovertyFilterState filterState = new PovertyFilterState(req);
        List<PovertyIndicator> list = povertyService.findAll(filterState.getSpecification());
        Map<String, Map<String, Double>> counterMap = new HashMap<>();
        AtomicInteger maxYear = new AtomicInteger(0);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            values.merge(COUNT, 1D, Double::sum);
            if (p.getPovertyLevel().getLabel().toLowerCase().equals("poor")
                    || p.getPovertyLevel().getLabel().toLowerCase().equals("very poor")) {
                values.merge(ACCUM, 1D, Double::sum);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });

        //Add indicator data
        counterMap.values().stream().forEach(v -> v.put(VALUE, v.get(ACCUM) / v.get(COUNT)));
        IndicatorData ret = new IndicatorData(counterMap.get(maxYear.toString()), counterMap.values());
        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get food loss summary data")
    @RequestMapping(value = "/foodLoss", method = {POST, GET})
    public IndicatorData getIndicatorFoodLoss(@ModelAttribute @Valid final FoodLossFilterPagingRequest req) {
        FoodLossFilterState filterState = new FoodLossFilterState(req);
        List<FoodLossIndicator> list = foodService.findAll(filterState.getSpecification());
        Map<String, Map<String, Double>> counterMap = new HashMap<>();
        AtomicInteger maxYear = new AtomicInteger(0);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            if (p.getLossType().getLabel().toLowerCase().equals("personal consumption")) {
                values.merge(COUNT, 1D, Double::sum);
                values.merge(ACCUM, p.getAvgPercentage(), Double::sum);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });
        //Add indicator data
        counterMap.values().stream().forEach(v -> v.put(VALUE, 1 - (v.get(ACCUM) / v.get(COUNT) / 100)));
        IndicatorData ret = new IndicatorData(counterMap.get(maxYear.toString()), counterMap.values());
        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get 'women in agricultural sector' summary data")
    @RequestMapping(value = "/agriculturalWomen", method = {POST, GET})
    public IndicatorData getIndicatorAgriculturalWomen(
            @ModelAttribute @Valid final AgriculturalWomenFilterPagingRequest req) {
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(req);
        List<AgriculturalWomenIndicator> list = womenService.findAll(filterState.getSpecification());
        Map<String, Map<String, Double>> counterMap = new HashMap<>();
        AtomicInteger maxYear = new AtomicInteger(0);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            if (p.getGender().getLabel().toLowerCase().equals("female")
                    && p.getGroup().getLabel().toLowerCase().equals("age group")) {
                values.merge(COUNT, 1D, Double::sum);
                values.merge(ACCUM, p.getPercentage(), Double::sum);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });
        //Add indicator data
        counterMap.values().stream().forEach(v -> v.put(VALUE, v.get(ACCUM) / v.get(COUNT) / 100));
        IndicatorData ret = new IndicatorData(counterMap.get(maxYear.toString()), counterMap.values());
        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get 'agriculture orientation index' summary data")
    @RequestMapping(value = "/aoi", method = {POST, GET})
    public IndicatorData getIndicatorAOI(
            @ModelAttribute @Valid final AOIFilterPagingRequest req) {
        AOIFilterState filterState = new AOIFilterState(req);
        List<AgricultureOrientationIndexIndicator> list = aoiService.findAll(filterState.getSpecification());
        Map<String, Map<String, Double>> counterMap = new HashMap<>();
        AtomicInteger maxYear = new AtomicInteger(0);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            if (p.getIndexType().getLabel().toLowerCase().equals("input subsidy ratio / gdp")) {
                values.put(VALUE, p.getSubsidies());
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });
        IndicatorData ret = new IndicatorData(counterMap.get(maxYear.toString()), counterMap.values());
        return ret;
    }

    class IndicatorData {

        private Map<String, Double> data;
        private List<Map<String, Double>> extraData;

        IndicatorData() {
        }

        IndicatorData(Map<String, Double> data) {
            this.data = data;
        }

        IndicatorData(Map<String, Double> data, Collection<Map<String, Double>> extraData) {
            this.data = data;
            this.extraData = new ArrayList<>(extraData);
        }

        public Map<String, Double> getData() {
            return data;
        }

        public void setData(Map<String, Double> data) {
            this.data = data;
        }

        public List<Map<String, Double>> getExtraData() {
            return extraData;
        }

        public void setExtraData(List<Map<String, Double>> extraData) {
            this.extraData = extraData;
        }
    }
}
