package org.devgateway.toolkit.persistence.dao.categories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
public class Product extends AbstractAuditableEntity implements Comparable<Product> {

    private static final Comparator<Product> NATURAL = Comparator.comparing(Product::getName);

    @NotNull
    @ManyToOne(optional = false)
    private ProductType productType;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    private MeasurementUnit unit;

    @NotNull @NotEmpty
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 100)
    private List<PriceType> priceTypes = new ArrayList<>();

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public List<PriceType> getPriceTypes() {
        return priceTypes;
    }

    public void setPriceTypes(List<PriceType> priceTypes) {
        this.priceTypes = priceTypes;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(Product o) {
        return NATURAL.compare(this, o);
    }
}
