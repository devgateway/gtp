package org.devgateway.toolkit.persistence.dao.categories;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
public class MarketType extends Category {

    public static final String RURAL_MARKET = "rural-market";
    public static final String FISHING_DOCK = "fishing-dock";
    public static final String TRANSFORMATION_PLACE = "transformation-place";

    public static final Map<String, Set<String>> PRODUCT_TYPES_BY_MARKET_TYPE = ImmutableMap.of(
            RURAL_MARKET,
            ImmutableSet.of(ProductType.CEREALS, ProductType.VEGETABLES, ProductType.FRUITS, ProductType.LIVESTOCK),

            FISHING_DOCK,
            ImmutableSet.of(ProductType.FRESH_FISH),

            TRANSFORMATION_PLACE,
            ImmutableSet.of(ProductType.PROCESSED_FISH));

    public static final Map<String, String> MARKET_TYPE_BY_PRODUCT_TYPE = PRODUCT_TYPES_BY_MARKET_TYPE.entrySet()
            .stream()
            .flatMap(e -> e.getValue().stream().map(pt -> Pair.of(e.getKey(), pt)))
            .collect(Collectors.toMap(Pair::getRight, Pair::getLeft));
}
