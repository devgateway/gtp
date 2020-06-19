package org.devgateway.toolkit.persistence.dao.categories;

import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import org.hibernate.annotations.BatchSize;
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

    public ProductType() {
    }

    public ProductType(Long id, String name, String label) {
        super(id, name, label);
    }

    @JsonIgnore
    public boolean areProductsOnSeparateRows() {
        return name.equals(ProductType.FRESH_FISH)
                || name.equals(ProductType.PROCESSED_FISH);
    }
}
