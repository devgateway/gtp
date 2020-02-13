package org.devgateway.toolkit.persistence.dto;


import org.devgateway.toolkit.persistence.dao.NationalIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

public class NationalIndicatorDTO {

    private String name;

    private String description;

    private String source;

    private String link;

    private Integer referenceYear;

    private Double referenceValue;

    private Double targetValue;

    private String measure;

    private List<YearValueDTO> yearValues = new ArrayList<>();

    public NationalIndicatorDTO(NationalIndicator indicator, String lang) {
        boolean isFr = lang != null && lang.equalsIgnoreCase(LANG_FR);
        this.name = isFr ? indicator.getNameFr() : indicator.getName();
        this.description = isFr ? indicator.getDescriptionFr() : indicator.getDescription();
        this.source = indicator.getSource();
        this.link = indicator.getLink();
        this.referenceYear = indicator.getReferenceYear();
        this.referenceValue = indicator.getReferenceValue();
        this.targetValue = indicator.getTargetValue();
        this.measure = indicator.getMeasure();
        List<YearValueDTO> yearValues = indicator.getYearValue().stream()
                .filter(n -> n != null)
                .map(n -> new YearValueDTO(n.getYear(), n.getValue(), this.targetValue))
                .collect(Collectors.toList());
        if (yearValues != null) {
            this.yearValues = yearValues;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getReferenceYear() {
        return referenceYear;
    }

    public void setReferenceYear(Integer referenceYear) {
        this.referenceYear = referenceYear;
    }

    public Double getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(Double referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Double targetValue) {
        this.targetValue = targetValue;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public List<YearValueDTO> getYearValues() {
        return yearValues;
    }

    public void setYearValues(List<YearValueDTO> yearValues) {
        this.yearValues = yearValues;
    }

    class YearValueDTO {

        private Integer year;
        private Double value;
        private Double targetPercent;

        YearValueDTO(Integer year, Double value, Double targetValue) {
            this.year = year;
            this.value = value;
            if (targetValue != null) {
                if (targetValue > value) {
                    targetPercent = value / targetValue * 100;
                } else {
                    targetPercent = targetValue / value * 100;
                }

            }
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Double getTargetPercent() {
            return targetPercent;
        }

        public void setTargetPercent(Double targetPercent) {
            this.targetPercent = targetPercent;
        }
    }
}
