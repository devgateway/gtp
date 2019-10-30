package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
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

    public static final String LEVEL_SUM = "levelSum";
    public static final String SCORE_SUM = "scoreSum";
    public static final String COUNT = "count";
    public static final String YEAR = "year";
    public static final String VALUE = "value";

    @Autowired
    private PovertyIndicatorService povertyService;

    @CrossOrigin
    @ApiOperation(value = "Get poverty summery data")
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
            values.merge(SCORE_SUM, p.getPovertyScore(), Double::sum);
            values.merge(COUNT, 1D, Double::sum);
            if (p.getPovertyLevel().getLabel().toLowerCase().equals("poor")
                    || p.getPovertyLevel().getLabel().toLowerCase().equals("very poor")) {
                values.merge(LEVEL_SUM, 1D, Double::sum);
            }
            if (maxYear.doubleValue() < p.getYear()) {
                maxYear.set(p.getYear());
            }
        });

        //Add indicator data
        counterMap.values().stream().forEach(v -> v.put(VALUE, v.get(LEVEL_SUM) / v.get(COUNT)));
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
