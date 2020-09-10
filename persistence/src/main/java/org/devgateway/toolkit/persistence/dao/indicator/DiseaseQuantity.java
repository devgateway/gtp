package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Month;
import java.util.Comparator;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_disease_quantity",
        columnNames = {"yearly_situation_id", "region_id", "disease_id", "month"}))
@JsonIgnoreProperties({"id", "new"})
public class DiseaseQuantity extends AbstractAuditableEntity implements Comparable<DiseaseQuantity> {
    private static final long serialVersionUID = -2329779081818606475L;

    private static final Comparator<DiseaseQuantity> NATURAL = Comparator.comparing(DiseaseQuantity::getRegion)
            .thenComparing(DiseaseQuantity::getDisease)
            .thenComparing(DiseaseQuantity::getMonth);

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private DiseaseYearlySituation yearlySituation;

    @NotNull
    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("regionId")
    private Region region;

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private LivestockDisease disease;

    @NotNull
    private Month month;

    @NotNull
    @Min(1)
    private Long quantity;

    public DiseaseQuantity() {
    }

    public DiseaseQuantity(Region region, LivestockDisease disease, Month month, Long quantity) {
        this.region = region;
        this.disease = disease;
        this.month = month;
        this.quantity = quantity;
    }

    public DiseaseYearlySituation getYearlySituation() {
        return yearlySituation;
    }

    public void setYearlySituation(DiseaseYearlySituation yearlySituation) {
        this.yearlySituation = yearlySituation;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public LivestockDisease getDisease() {
        return disease;
    }

    public void setDisease(LivestockDisease disease) {
        this.disease = disease;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(DiseaseQuantity diseaseQuantity) {
        return NATURAL.compare(this, diseaseQuantity);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
