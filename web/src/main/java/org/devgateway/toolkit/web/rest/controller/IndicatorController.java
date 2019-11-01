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
import java.util.concurrent.atomic.AtomicBoolean;
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
    public static final String FEMALE = "female";
    public static final String AGE_GROUP = "age group";
    public static final String PERSONAL_CONSUMPTION = "personal consumption";
    public static final String VERY_POOR = "very poor";
    public static final String POOR = "poor";
    public static final String POVERTY = "poverty";
    public static final String FOOD_LOSS = "foodLoss";
    public static final String AGRICULTURAL_WOMEN = "agriculturalWomen";
    public static final String AG_ORIENTATION = "agOrientation";
    public static final String INPUT_SUBSIDY = "input subsidy ratio / gdp";

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
        ret.put(POVERTY, getIndicatorPoverty(new PovertyFilterPagingRequest(req)));
        ret.put(FOOD_LOSS, getIndicatorFoodLoss(new FoodLossFilterPagingRequest(req)));
        ret.put(AGRICULTURAL_WOMEN, getIndicatorAgriculturalWomen(new AgriculturalWomenFilterPagingRequest(req)));
        ret.put(AG_ORIENTATION, getIndicatorAOI(new AOIFilterPagingRequest(req)));
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
        AtomicBoolean dataFlag = new AtomicBoolean(false);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            values.merge(COUNT, 1D, Double::sum);
            if (p.getPovertyLevel().getLabel().toLowerCase().equals(POOR)
                    || p.getPovertyLevel().getLabel().toLowerCase().equals(VERY_POOR)) {
                values.merge(ACCUM, 1D, Double::sum);
                dataFlag.set(true);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });

        //Add indicator data
        if (dataFlag.get()) {
            counterMap.values().stream().forEach(v -> v.put(VALUE, v.get(ACCUM) / v.get(COUNT)));
        }
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
        AtomicBoolean dataFlag = new AtomicBoolean(false);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            if (p.getLossType().getLabel().toLowerCase().equals(PERSONAL_CONSUMPTION)) {
                values.merge(COUNT, 1D, Double::sum);
                values.merge(ACCUM, p.getAvgPercentage(), Double::sum);
                dataFlag.set(true);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });
        //Add indicator data
        if (dataFlag.get()) {
            counterMap.values().stream().forEach(v -> v.put(VALUE, 1 - (v.get(ACCUM) / v.get(COUNT) / 100)));
        }
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
        AtomicBoolean dataFlag = new AtomicBoolean(false);
        list.stream().forEach(p -> {
            Map<String, Double> values = counterMap.get(p.getYear().toString());
            if (values == null) {
                values = new HashMap<>();
                values.put(YEAR, p.getYear().doubleValue());
                counterMap.put(p.getYear().toString(), values);
            }
            if (p.getGender().getLabel().toLowerCase().equals(FEMALE)
                    && p.getGroup().getLabel().toLowerCase().equals(AGE_GROUP)) {
                values.merge(COUNT, 1D, Double::sum);
                values.merge(ACCUM, p.getPercentage(), Double::sum);
                dataFlag.set(true);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });
        //Add indicator data
        if (dataFlag.get()) {
            counterMap.values().stream().forEach(v -> v.put(VALUE, v.get(ACCUM) / v.get(COUNT) / 100));
        }
        IndicatorData ret = new IndicatorData(counterMap.get(maxYear.toString()), counterMap.values());
        return ret;
    }

    @CrossOrigin
    @ApiOperation(value = "Get 'agriculture orientation index' summary data")
    @RequestMapping(value = "/agOrientation", method = {POST, GET})
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
            if (p.getIndexType().getLabel().toLowerCase().equals(INPUT_SUBSIDY)) {
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
