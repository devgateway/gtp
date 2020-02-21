package org.devgateway.toolkit.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

public class RegionIndicatorDTO {

    private Long id;

    private String name;

    @JsonIgnore
    private String nameEnFr;

    private String description;

    private Integer year;

    private Collection<RegionStatDTO> stats;

    private Double minValue;

    private Double maxValue;

    private Boolean leftMap;

    private Boolean rightMap;

    @JsonIgnore
    private Set<String> sources = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    public RegionIndicatorDTO() {

    }

    public RegionIndicatorDTO(final RegionIndicator ri, final String lang) {
        boolean isFr = StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR);
        this.id = ri.getId();
        this.name = isFr || StringUtils.isBlank(ri.getName()) ? ri.getNameFr() : ri.getName();
        this.nameEnFr = ri.getFullNameYear();
        this.description = isFr ? ri.getDescriptionFr() : ri.getDescription();
        this.year = ri.getYear();
        this.stats = ri.getStats().stream().map(rs -> new RegionStatDTO(rs)).collect(Collectors.toList());
        this.minValue = ri.getMinValue();
        this.maxValue = ri.getMaxValue();
        this.leftMap = ri.isLeftMap();
        this.rightMap = ri.isRightMap();
        if (ri.getSource() != null) {
            this.sources.add(ri.getSource());
        }
    }

    public String getNameEnFr() {
        return nameEnFr;
    }

    public void setNameEnFr(String nameEnFr) {
        this.nameEnFr = nameEnFr;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Collection<RegionStatDTO> getStats() {
        return stats;
    }

    public void setStats(Collection<RegionStatDTO> stats) {
        this.stats = stats;
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
