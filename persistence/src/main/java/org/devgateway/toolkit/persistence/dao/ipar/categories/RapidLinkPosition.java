package org.devgateway.toolkit.persistence.dao.ipar.categories;

import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/*
 * @author Daniel Oliva
 */
// @Entity
 @Audited
public class RapidLinkPosition extends Category {
    @Override
    public String toString() {
        return getLabelFr() + "/" + getLabel();
    }
}