package org.devgateway.toolkit.persistence.dao.categories;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/*
 * @author Daniel Oliva
 */
@Entity
@Audited
public class RapidLinkPosition extends Category {
    @Override
    public String toString() {
        return getLabelFr() + "/" + getLabel();
    }
}
