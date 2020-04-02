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

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.dao.OrganizationTag;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author mpostelnicu
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class Organization extends AbstractAuditableEntity implements Serializable, Labelable {

    @Enumerated(EnumType.STRING)
    private OrganizationTag tag;

    private String label;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<IndicatorMetadata> indicators = new ArrayList<>();

    public OrganizationTag getTag() {
        return tag;
    }

    public void setTag(OrganizationTag tag) {
        this.tag = tag;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getLabel(String lang) {
        return label;
    }

    public List<IndicatorMetadata> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorMetadata> indicators) {
        this.indicators = indicators;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

}
