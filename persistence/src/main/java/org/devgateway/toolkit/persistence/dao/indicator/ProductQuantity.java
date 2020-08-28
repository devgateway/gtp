package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.converter.MonthDayStringAttributeConverter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.Comparator;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"product_yearly_prices_id", "product_id", "market_id", "monthDay"}))
@JsonIgnoreProperties({"id", "new"})
public class ProductQuantity extends AbstractAuditableEntity implements Comparable<ProductQuantity> {
    private static final long serialVersionUID = -8732818467335806147L;

    private static final Comparator<ProductQuantity> NATURAL = Comparator.comparing(ProductQuantity::getProduct)
            .thenComparing(ProductQuantity::getMarket)
            .thenComparing(ProductQuantity::getMonthDay);

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private ProductYearlyPrices productYearlyPrices;

    @NotNull
    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("productId")
    private Product product;

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private Market market;

    @NotNull
    @Column(columnDefinition = "varchar", length = 6)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay monthDay;

    @NotNull
    @Column(scale = 1, precision = 10)
    @Min(0)
    private BigDecimal quantity;

    public ProductQuantity() {
    }

    public ProductQuantity(Product product, Market market, MonthDay monthDay, BigDecimal quantity) {
        this.product = product;
        this.market = market;
        this.monthDay = monthDay;
        this.quantity = quantity;
    }

    public ProductYearlyPrices getProductYearlyPrices() {
        return productYearlyPrices;
    }

    public void setProductYearlyPrices(ProductYearlyPrices productYearlyPrices) {
        this.productYearlyPrices = productYearlyPrices;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    @JsonIgnore
    public AbstractAuditableEntity getParent() {
        return productYearlyPrices;
    }

    @Override
    public int compareTo(ProductQuantity o) {
        return NATURAL.compare(this, o);
    }

    @JsonIgnore
    public String getQuantityNaturalId() {
        return String.format("%d-%d-%s", this.getProduct().getId(), this.getMarket().getId(),
                this.getMonthDay().toString());
    }
}
