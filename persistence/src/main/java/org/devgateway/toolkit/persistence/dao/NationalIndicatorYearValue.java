package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Entity
@JsonIgnoreProperties({"new"})
public class NationalIndicatorYearValue extends AbstractAuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private NationalIndicator nationalIndicator;

    private Integer year;

    private Double value;

    public NationalIndicator getNationalIndicator() {
        return nationalIndicator;
    }

    public void setNationalIndicator(NationalIndicator nationalIndicator) {
        this.nationalIndicator = nationalIndicator;
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

    @Override
    public AbstractAuditableEntity getParent() {
        return nationalIndicator;
    }
}
