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
@JsonIgnoreProperties({"id", "new"})
public class RegionIndicator extends GenericPersistable implements Serializable {

    private String name;

    private String description;

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

    @JsonIgnore
    public String getReducedName() {
        String ret;
        if (name != null && name.length() > 40) {
            ret = name.substring(0, 39) + "...";
        } else {
            ret = name;
        }
        return ret;
    }


    @JsonIgnore
    public String getReducedDesc() {
        String ret;
        if (description != null && description.length() > 60) {
            ret = description.substring(0, 59) + "...";
        } else {
            ret = description;
        }
        return ret;
    }
}
