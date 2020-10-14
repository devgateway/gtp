package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<RainfallMapLayer> layers = new HashSet<>();

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

    public Set<RainfallMapLayer> getLayers() {
        return layers;
    }

    public void setLayers(Set<RainfallMapLayer> layers) {
        this.layers = layers;
    }

    public RainfallMapLayer computeIfAbsent(RainfallMapLayerType type) {
        return this.layers.stream().filter(l -> l.getType().equals(type)).findFirst().orElseGet(() -> {
            RainfallMapLayer l = new RainfallMapLayer();
            this.layers.add(l);
            l.setDecadalRainfallMap(this);
            l.setType(type);
            return l;
        });
    }

    @Override
    public boolean isEmpty() {
        return layers.isEmpty();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

}
