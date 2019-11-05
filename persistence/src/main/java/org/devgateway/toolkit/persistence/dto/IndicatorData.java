package org.devgateway.toolkit.persistence.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class IndicatorData {
    private Map<String, Double> data;
    private List<Map<String, Double>> extraData;

    public IndicatorData() {
    }

    public IndicatorData(Map<String, Double> data) {
        this.data = data;
    }

    public IndicatorData(Map<String, Double> data, Collection<Map<String, Double>> extraData) {
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
