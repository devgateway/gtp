package org.devgateway.toolkit.persistence.dao.indicator;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiseaseYearlySituation extends AbstractAuditableEntity implements AbstractImportableEntity {
    private static final long serialVersionUID = 4943166344788175189L;

    @NotNull
    @Column(unique = true)
    private Integer year;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "yearlySituation")
    @SortNatural
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 10)
    private SortedSet<DiseaseQuantity> quantities = new TreeSet<>();

    @Formula("(select count(*) from disease_quantity dq where dq.yearly_situation_id = id)")
    @NotAudited
    private Long quantitiesSize;

    public DiseaseYearlySituation() {
    }

    public DiseaseYearlySituation(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SortedSet<DiseaseQuantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(SortedSet<DiseaseQuantity> quantities) {
        this.quantities = quantities;
    }

    public boolean addDiseaseQuantity(DiseaseQuantity diseaseQuantity) {
        diseaseQuantity.setYearlySituation(this);
        return quantities.add(diseaseQuantity);
    }

    public Long getQuantitiesSize() {
        return quantitiesSize;
    }

    public void setQuantitiesSize(Long quantitiesSize) {
        this.quantitiesSize = quantitiesSize;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return getQuantitiesSize() == 0;
    }
}
