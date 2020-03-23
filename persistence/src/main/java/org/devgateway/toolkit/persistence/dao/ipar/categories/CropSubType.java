package org.devgateway.toolkit.persistence.dao.ipar.categories;

import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * @author Daniel Oliva
 */
@Entity
@Audited
public class CropSubType extends Category {

    public CropSubType() {
    }

    public CropSubType(Long id, String label) {
        super(label);
        setId(id);
    }
}
