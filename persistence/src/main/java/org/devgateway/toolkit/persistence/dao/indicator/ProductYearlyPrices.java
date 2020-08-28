package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"year", "product_type_id"}))
public class ProductYearlyPrices extends AbstractAuditableEntity implements AbstractImportableEntity {
    private static final long serialVersionUID = 8346612796346268316L;

    @NotNull
    private Integer year;

    @NotNull
    @ManyToOne(optional = false)
    private ProductType productType;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productYearlyPrices")
    @SortNatural
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 10)
    private SortedSet<ProductPrice> prices = new TreeSet<>();

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productYearlyPrices")
    @SortNatural
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 10)
    private SortedSet<ProductQuantity> quantities = new TreeSet<>();

    /**
     * Used in some cases to improve performance by avoiding to load prices. If the value is set it matches the
     * size of the persisted collection.
     */
    @Transient
    @JsonIgnore
    private Long pricesSize;

    /**
     * Used in some cases to improve performance by avoiding to load prices. If the value is set it matches the
     * size of the persisted collection.
     */
    @Transient
    @JsonIgnore
    private Long quantitiesSize;

    public ProductYearlyPrices() {
    }

    public ProductYearlyPrices(Integer year, ProductType productType) {
        this.year = year;
        this.productType = productType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public SortedSet<ProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(SortedSet<ProductPrice> prices) {
        this.prices = prices;
    }

    public boolean addPrice(ProductPrice price) {
        price.setProductYearlyPrices(this);
        return prices.add(price);
    }

    public void setPricesSize(Long pricesSize) {
        this.pricesSize = pricesSize;
    }

    public void setQuantitiesSize(Long quantitiesSize) {
        this.quantitiesSize = quantitiesSize;
    }

    public SortedSet<ProductQuantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(SortedSet<ProductQuantity> quantities) {
        this.quantities = quantities;
    }

    public boolean addQuantity(ProductQuantity quantity) {
        quantity.setProductYearlyPrices(this);
        return quantities.add(quantity);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (pricesSize != null)
                ? pricesSize == 0L && quantitiesSize == 0L
                : prices.isEmpty() && quantities.isEmpty();
    }
}
