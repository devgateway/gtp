package org.devgateway.toolkit.persistence.dao.categories;

import javax.persistence.Entity;

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
}
