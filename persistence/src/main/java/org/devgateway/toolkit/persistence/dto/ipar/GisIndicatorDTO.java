package org.devgateway.toolkit.persistence.dto.ipar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.ipar.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.RegionIndicator;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

public class GisIndicatorDTO {

    private Long id;

    private String name;

    @JsonIgnore
    private String nameEnFr;

    private String description;

    private String measure;

    private String indicatorGroup;

    private Integer year;

    private Collection<GisStatDTO> stats;

    private Double minValue;

    private Double maxValue;

    private Boolean leftMap;

    private Boolean rightMap;

    private Boolean reverse;

    @JsonIgnore
    private Set<String> sources = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    public GisIndicatorDTO() {

    }

    public GisIndicatorDTO(final RegionIndicator ri, final String lang) {
        boolean isFr = StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR);
        this.id = ri.getId();
        this.name = isFr || StringUtils.isBlank(ri.getName()) ? ri.getNameFr() : ri.getName();
        this.nameEnFr = ri.getFullNameYear();
        this.description = isFr ? ri.getDescriptionFr() : ri.getDescription();
        this.year = ri.getYear();
        this.stats = ri.getStats().stream().map(rs -> new GisStatDTO(rs)).collect(Collectors.toList());
        this.minValue = ri.getMinValue();
        this.maxValue = ri.getMaxValue();
        this.leftMap = ri.isLeftMap();
        this.measure = ri.getMeasure();
        //TODO
        this.indicatorGroup = this.name.split(" ")[0];
        this.rightMap = ri.isRightMap();
        if (ri.getSource() != null) {
            this.sources.add(ri.getSource());
        }
        this.reverse = ri.isDescending();
    }

    public GisIndicatorDTO(final DepartmentIndicator di, final String lang) {
        boolean isFr = StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR);
        this.id = di.getId();
        this.name = isFr || StringUtils.isBlank(di.getName()) ? di.getNameFr() : di.getName();
        this.nameEnFr = di.getFullNameYear();
        this.description = isFr ? di.getDescriptionFr() : di.getDescription();
        this.year = di.getYear();
        this.stats = di.getStats().stream().map(rs -> new GisStatDTO(rs)).collect(Collectors.toList());
        this.minValue = di.getMinValue();
        this.maxValue = di.getMaxValue();
        this.leftMap = di.isLeftMap();
        this.measure = di.getMeasure();
        //TODO
        this.indicatorGroup = this.name.split(" ")[0];
        this.rightMap = di.isRightMap();
        if (di.getSource() != null) {
            this.sources.add(di.getSource());
        }
        this.reverse = di.isDescending();
    }

    public String getNameEnFr() {
        return nameEnFr;
    }

    public void setNameEnFr(String nameEnFr) {
        this.nameEnFr = nameEnFr;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIndicatorGroup() {
        return indicatorGroup;
    }

    public void setIndicatorGroup(String indicatorGroup) {
        this.indicatorGroup = indicatorGroup;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Collection<GisStatDTO> getStats() {
        return stats;
    }

    public void setStats(Collection<GisStatDTO> stats) {
        this.stats = stats;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean getLeftMap() {
        return leftMap;
    }

    public void setLeftMap(Boolean leftMap) {
        this.leftMap = leftMap;
    }

    public Boolean getRightMap() {
        return rightMap;
    }

    public void setRightMap(Boolean rightMap) {
        this.rightMap = rightMap;
    }

    public Set<String> getSources() {
        return sources;
    }

    public void setSources(Set<String> sources) {
        this.sources = sources;
    }

    public String getSource() {
        if (!sources.isEmpty()) {
            return String.join(", ", sources);
        }
        return "";
    }
}
