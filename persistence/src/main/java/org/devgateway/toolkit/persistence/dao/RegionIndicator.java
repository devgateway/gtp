package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Daniel Oliva
 */
@Entity
@JsonIgnoreProperties({"new"})
public class RegionIndicator extends GenericPersistable implements Serializable {

    private String name;

    private String nameFr;

    private String description;

    private String descriptionFr;

    private String measure;

    private Integer year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "regionIndicator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<RegionStat> stats;

    private Double minValue;

    private Double maxValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Person uploadedBy;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> fileMetadata;

    private boolean leftMap;

    private boolean rightMap;

    private String source;

    private boolean approved;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Collection<RegionStat> getStats() {
        return stats;
    }

    public void setStats(Collection<RegionStat> stats) {
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

    public Set<FileMetadata> getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(Set<FileMetadata> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public Person getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Person uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @JsonIgnore
    public String getReducedName() {
        return getReducedStr(name, 25);
    }

    @JsonIgnore
    public String getReducedNameFr() {
        return getReducedStr(nameFr, 25);
    }

    @JsonIgnore
    public String getReducedDesc() {
        return getReducedStr(description, 40);
    }

    @JsonIgnore
    public String getReducedDescFr() {
        return getReducedStr(descriptionFr, 40);
    }

    @JsonIgnore
    public String getReducedStr(String label, int length) {
        String ret = label;
        if (label != null && label.length() > length) {
            ret = label.substring(0, length - 1) + "...";
        }
        return ret;
    }

    public boolean isLeftMap() {
        return leftMap;
    }

    public void setLeftMap(boolean leftMap) {
        this.leftMap = leftMap;
    }

    public boolean isRightMap() {
        return rightMap;
    }

    public void setRightMap(boolean rightMap) {
        this.rightMap = rightMap;
    }
}
