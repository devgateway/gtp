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

import java.util.Comparator;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"new"})
@Table(indexes = {@Index(columnList = "label"), @Index(columnList = "DTYPE")})
public class Category extends AbstractAuditableEntity implements Labelable, Comparable<Category> {

    static final int DTYPE_COLUMN_LENGTH = 100;

    private static final Comparator<Category> NATURAL = Comparator.comparing(Category::getLabel);

    private static final long serialVersionUID = 1L;

    /**
     * A non-modifiable name used to reference this category item from code.
     */
    protected String name;

    protected String label;

    private String description;

    public Category(final String label) {
        this(null, null, label);
    }

    public Category() {
    }

    public Category(Long id, String name, String label) {
        setId(id);
        this.name = name;
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }

    public String getLabel(String language) {
        return label;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Category o) {
        return NATURAL.compare(this, o);
    }
}
