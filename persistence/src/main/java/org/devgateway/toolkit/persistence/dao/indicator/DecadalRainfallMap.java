package org.devgateway.toolkit.persistence.dao.indicator;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class DecadalRainfallMap extends AbstractAuditableEntity implements AbstractImportableEntity {
    private static final long serialVersionUID = -6262299838960967694L;

    @NotNull
    @Column(nullable = false)
    private Integer year;

    @NotNull
    @Column(nullable = false)
    private Month month;

    @NotNull
    @Column(nullable = false)
    private Decadal decadal;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "decadalRainfallMap")
    private RainfallMap cumulative = new RainfallMap();

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "decadalRainfallMap")
    private RainfallMap abnormal = new RainfallMap();

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public MonthDTO getDisplayMonth() {
        return MonthDTO.of(month);
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }

    public RainfallMap getCumulative() {
        return cumulative;
    }

    public void setCumulative(RainfallMap cumulative) {
        this.cumulative = cumulative;
    }

    public RainfallMap getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(RainfallMap abnormal) {
        this.abnormal = abnormal;
    }

    @Override
    public boolean isEmpty() {
        return cumulative.isEmpty() && abnormal.isEmpty();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

}
