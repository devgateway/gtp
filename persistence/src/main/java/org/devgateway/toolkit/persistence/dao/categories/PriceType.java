package org.devgateway.toolkit.persistence.dao.categories;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
public class PriceType extends Category {
    private static final long serialVersionUID = -4435417721030612488L;

    public static final String NAME_RETAIL = "retail-price";

    public PriceType() {
    }

    public PriceType(Long id, String name, String label) {
        super(id, name, label);
    }
}
