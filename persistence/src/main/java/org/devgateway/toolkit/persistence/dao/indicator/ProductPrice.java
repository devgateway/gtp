package org.devgateway.toolkit.persistence.dao.indicator;

import java.time.MonthDay;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.converter.MonthDayStringAttributeConverter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"product_yearly_prices_id", "product_id", "market_id", "monthDay", "price_type_id"}))
public class ProductPrice extends AbstractAuditableEntity implements Comparable<ProductPrice> {

    private static final Comparator<ProductPrice> NATURAL = Comparator.comparing(ProductPrice::getProduct)
            .thenComparing(ProductPrice::getMarket)
            .thenComparing(ProductPrice::getMonthDay)
            .thenComparing(ProductPrice::getPriceType);

    @NotNull
    @ManyToOne(optional = false)
    private ProductYearlyPrices productYearlyPrices;

    @NotNull
    @ManyToOne(optional = false)
    private Product product;

    @NotNull
    @ManyToOne(optional = false)
    private Market market;

    @NotNull
    @Column(columnDefinition = "varchar", length = 6)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay monthDay;

    @NotNull
    @ManyToOne(optional = false)
    private PriceType priceType;

    @NotNull @Min(0)
    private Integer price;

    public ProductPrice() {
    }

    public ProductPrice(
            @NotNull Product product,
            @NotNull Market market, @NotNull MonthDay monthDay,
            @NotNull PriceType priceType,
            @NotNull @Min(0) Integer price) {
        this.product = product;
        this.market = market;
        this.monthDay = monthDay;
        this.priceType = priceType;
        this.price = price;
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

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return productYearlyPrices;
    }

    @Override
    public int compareTo(ProductPrice o) {
        return NATURAL.compare(this, o);
    }
}
