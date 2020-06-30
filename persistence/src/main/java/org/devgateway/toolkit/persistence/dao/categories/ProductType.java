package org.devgateway.toolkit.persistence.dao.categories;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.ImmutableList;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@BatchSize(size = 100)
public class ProductType extends Category {

    public static final String CEREALS = "cereals";
    public static final String VEGETABLES = "vegetables";
    public static final String FRUITS = "fruits";
    public static final String LIVESTOCK = "livestock";
    public static final String FRESH_FISH = "fresh-fish";
    public static final String PROCESSED_FISH = "processed-fish";

    public static final List<String> ALL =
            ImmutableList.of(CEREALS, VEGETABLES, FRUITS, LIVESTOCK, FRESH_FISH, PROCESSED_FISH);

    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 20)
    @JsonProperty("marketTypeId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private MarketType marketType;

    public ProductType() {
    }

    public ProductType(Long id, String name, String label, MarketType marketType) {
        super(id, name, label);
        this.marketType = marketType;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    @JsonIgnore
    public boolean areProductsOnSeparateRows() {
        return name.equals(ProductType.FRESH_FISH)
                || name.equals(ProductType.PROCESSED_FISH);
    }
}
