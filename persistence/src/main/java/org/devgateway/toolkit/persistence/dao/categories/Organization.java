/*******************************************************************************
 * Copyright (c) 2015 Development Gateway, Inc and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 *
 * Contributors:
 * Development Gateway - initial API and implementation
 *******************************************************************************/
package org.devgateway.toolkit.persistence.dao.categories;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * 
 * @author mpostelnicu
 *
 */
@Entity
@Audited
public class Organization extends Category {

    public Organization() {
        super();
    }

    public Organization(final String label) {
        super();
        this.label = label;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }
}
