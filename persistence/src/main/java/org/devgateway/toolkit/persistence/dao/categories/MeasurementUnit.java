package org.devgateway.toolkit.persistence.dao.categories;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
public class MeasurementUnit extends Category {

    public static final String KG = "kg";
    public static final String HEAD = "head";

    public MeasurementUnit() {
    }

    public MeasurementUnit(Long id, String name, String label) {
        super(id, name, label);
    }
}
