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
package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author mpostelnicu
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(indexes = {@Index(columnList = "authority")})
public class Role extends GenericPersistable implements Comparable<Role>, Labelable {
    private static final long serialVersionUID = -6007958105920327142L;

    private String authority;

    private String label;

    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public Role() {
    }

    public Role(final String authority) {
        this.authority = authority;
    }

    /**
     * @param authority the authority to set
     */
    public void setAuthority(final String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public int compareTo(final Role o) {
        return this.authority.compareTo(o.getAuthority());
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getLabel(String lang) {
        return getLabel();
    }

}
