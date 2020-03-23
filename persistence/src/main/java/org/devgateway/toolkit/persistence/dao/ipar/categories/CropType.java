package org.devgateway.toolkit.persistence.dao.ipar.categories;

import javax.persistence.Entity;

import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
public class CropType extends Category {

    public CropType() {
    }

    public CropType(Long id, String label) {
        super(label);
        setId(id);
    }
}
