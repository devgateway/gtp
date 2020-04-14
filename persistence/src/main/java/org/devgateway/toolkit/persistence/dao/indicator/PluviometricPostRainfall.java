package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class PluviometricPostRainfall extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 5028052088050707223L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private DecadalRainfall decadalRainfall;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pluviometricPostRainfall")
    @JsonIgnore
    private List<Rainfall> rainfalls = new ArrayList<>();

    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public DecadalRainfall getDecadalRainfall() {
        return decadalRainfall;
    }

    public void setDecadalRainfall(DecadalRainfall decadalRainfall) {
        this.decadalRainfall = decadalRainfall;
        this.decadalRainfall.getPostRainfalls().add(this);
    }

    public List<Rainfall> getRainfalls() {
        return rainfalls;
    }

    public void setRainfalls(List<Rainfall> rainfalls) {
        this.rainfalls = rainfalls;
    }

    public Double getTotal() {
        return rainfalls.stream().mapToDouble(r -> r.getRain() == null ? 0 : r.getRain()).sum();
    }

    public Long getRainyDaysCount() {
        return rainfalls.stream().filter(r -> r.getRain() != null).count();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
