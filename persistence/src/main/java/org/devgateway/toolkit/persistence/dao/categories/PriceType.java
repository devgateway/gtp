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

    public static final String RETAIL_PRICE_NAME = "retail-price";
    public static final String HEAD_PRICE_NAME = "head-price";

    public PriceType() {
    }

    public PriceType(Long id, String name, String label) {
        super(id, name, label);
    }
}
