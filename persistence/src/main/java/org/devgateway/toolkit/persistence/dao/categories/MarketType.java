package org.devgateway.toolkit.persistence.dao.categories;

import java.util.List;

import javax.persistence.Entity;

import com.google.common.collect.ImmutableList;
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

    public static final List<String> ALL = ImmutableList.of(RURAL_MARKET, FISHING_DOCK, TRANSFORMATION_PLACE);

    public MarketType() {
    }

    public MarketType(long id, String name, String label) {
        super(id, name, label);
    }
}
