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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author idobre
 * @since 11/18/14
 */

@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorColumn(length = Category.DTYPE_COLUMN_LENGTH)
@Table(indexes = {@Index(columnList = "label"), @Index(columnList = "DTYPE")})
public class Category extends AbstractAuditableEntity implements Serializable, Labelable {

    static final int DTYPE_COLUMN_LENGTH = 100;

    private static final long serialVersionUID = 1L;

    protected String label;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private List<LocalizedCategoryLabel> localizedLabels = new ArrayList<>();

    public Category(final String label) {
        this.label = label;
    }

    public Category() {

    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }

    public List<LocalizedCategoryLabel> getLocalizedLabels() {
        return localizedLabels;
    }

    public void setLocalizedLabels(List<LocalizedCategoryLabel> localizedLabels) {
        this.localizedLabels = localizedLabels;
    }

    /**
     * Retrieve french label. If not found returns null.
     */
    @JsonIgnore
    public String getLabelFr() {
        return getLabel("fr");
    }

    @JsonIgnore
    private String getLabel(String language) {
        for (LocalizedCategoryLabel localizedLabel : localizedLabels) {
            if (localizedLabel.getLanguage().equals(language)) {
                return localizedLabel.getLabel();
            }
        }
        return label; //Dont return null for label, its being used in serialization
    }

    /**
     * Set label for french language.
     */
    public void setLabelFr(String label) {
        setLabel("fr", label);
    }

    private void setLabel(String language, String label) {
        LocalizedCategoryLabel target = null;
        for (LocalizedCategoryLabel localizedLabel : localizedLabels) {
            if (localizedLabel.getLanguage().equals(language)) {
                target = localizedLabel;
            }
        }
        if (target == null) {
            target = new LocalizedCategoryLabel();
            target.setCategory(this);
            target.setLanguage(language);
            localizedLabels.add(target);
        }
        target.setLabel(label);
    }

    /**
     * Returns a label for localization purposes. Fallback to {@link Category#label} if a
     * {@link LocalizedCategoryLabel} for requested language was not found.
     */
    public String getLocalizedLabel(String language) {
        String label = getLabel(language);
        if (label != null) {
            return label;
        } else {
            return this.label;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
