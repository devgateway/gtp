package org.devgateway.toolkit.persistence.dao.categories;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
public class LivestockDisease extends Category {
    private static final long serialVersionUID = 8355230825811512846L;
}
