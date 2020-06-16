package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class DecadalRainfall extends AbstractStatusAuditableEntity implements Serializable {
    private static final long serialVersionUID = -4244337786708697078L;

    public static final double MAX_RAIN = 1000;

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "decadalRainfall")
    @JsonIgnore
    private List<PluviometricPostRainfall> postRainfalls = new ArrayList<>();

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

    public int lengthOfMonth() {
        return MonthDTO.of(month).lengthOfMonth(year);
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }

    public List<PluviometricPostRainfall> getPostRainfalls() {
        return postRainfalls;
    }

    public void setPostRainfalls(List<PluviometricPostRainfall> postRainfalls) {
        this.postRainfalls = postRainfalls;
    }

    public void addPostRainfall(PluviometricPostRainfall pluviometricPostRainfall) {
        pluviometricPostRainfall.setDecadalRainfall(this);
        postRainfalls.add(pluviometricPostRainfall);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
