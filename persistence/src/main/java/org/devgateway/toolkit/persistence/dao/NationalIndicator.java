package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Entity
@JsonIgnoreProperties({"new"})
public class NationalIndicator extends AbstractAuditableEntity implements Serializable, Linkable {

    private String name;

    private String nameFr;

    @Column(length = 1024)
    private String description;

    @Column(length = 1024)
    private String descriptionFr;

    private String source;

    @Column(length = 1024)
    private String link;

    private Integer referenceYear;

    private Double referenceValue;

    private Double targetValue;

    private Integer targetYear;

    private String measure;

    private Boolean approved;

    private Boolean descending;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Person uploadedBy;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "nationalIndicator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderColumn(name = "index")
    private List<NationalIndicatorYearValue> yearValue;

    public NationalIndicator() {
        yearValue = new ArrayList<>();
    }

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

    @JsonIgnore
    public String getNameEnFr() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(name)) {
            sb.append(name);
            if (StringUtils.isNotBlank(nameFr)) {
                sb.append("/");
            }
        }
        if (StringUtils.isNotBlank(nameFr)) {
            sb.append(nameFr);
        }
        return sb.toString();
    }

    public boolean isDescending() {
        return descending != null ? descending : false;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
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

    public Integer getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(Integer targetYear) {
        this.targetYear = targetYear;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public List<NationalIndicatorYearValue> getYearValue() {
        return yearValue;
    }

    public void setYearValue(List<NationalIndicatorYearValue> yearValue) {
        this.yearValue = yearValue;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Person getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Person uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
