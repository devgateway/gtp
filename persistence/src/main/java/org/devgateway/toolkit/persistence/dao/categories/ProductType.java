package org.devgateway.toolkit.persistence.dao.categories;

import static org.devgateway.toolkit.persistence.dao.categories.PriceType.HEAD_PRICE_NAME;
import static org.devgateway.toolkit.persistence.dao.categories.PriceType.RETAIL_PRICE_NAME;

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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@BatchSize(size = 100)
public class ProductType extends Category {
    private static final long serialVersionUID = -8902924493505214722L;

    public static final String CEREALS = "cereals";
    public static final String VEGETABLES = "vegetables";
    public static final String FRUITS = "fruits";
    public static final String LIVESTOCK = "livestock";
    public static final String FRESH_FISH = "fresh-fish";
    public static final String PROCESSED_FISH = "processed-fish";

    public static final List<String> ALL =
            ImmutableList.of(CEREALS, VEGETABLES, FRUITS, LIVESTOCK, FRESH_FISH, PROCESSED_FISH);

    public static final Map<String, String> PRODUCT_PRICE_TYPE = Stream.of(new String[][]{
            {CEREALS, RETAIL_PRICE_NAME},
            {VEGETABLES, RETAIL_PRICE_NAME},
            {FRUITS, RETAIL_PRICE_NAME},
            {LIVESTOCK, HEAD_PRICE_NAME},
            {FRESH_FISH, RETAIL_PRICE_NAME},
            {PROCESSED_FISH, RETAIL_PRICE_NAME},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

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
