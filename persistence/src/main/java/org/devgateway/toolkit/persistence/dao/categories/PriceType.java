package org.devgateway.toolkit.persistence.dao.categories;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
public class PriceType extends Category {

    public PriceType() {
    }

    public PriceType(Long id, String name, String label) {
        super(id, name, label);
    }
}
