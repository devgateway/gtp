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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.dao.ipar.categories.LocalizedCategoryLabel;
import org.devgateway.toolkit.persistence.util.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import java.util.Comparator;
import java.util.List;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

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

    /*
    private Integer type;
     */

    /*
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 100)
    @JsonIgnore
    private List<LocalizedCategoryLabel> localizedLabels = new ArrayList<>();
    */

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
        return null;
    }

    public void setLocalizedLabels(List<LocalizedCategoryLabel> localizedLabels) {
        // this.localizedLabels = localizedLabels;
    }

    /**
     * Retrieve french label. If not found returns null.
     */
    @JsonIgnore
    public String getLabelFr() {
        return getLabel(LANG_FR);
    }

    public String getLabel(String language) {
        /*
        if (StringUtils.isNotBlank(language)) {
            try {
                for (LocalizedCategoryLabel localizedLabel : localizedLabels) {
                    if (localizedLabel.getLanguage().equals(language)) {
                        return localizedLabel.getLabel();
                    }
                }
            } catch (LazyInitializationException e) {
                return label;
                //Dont return null for label, its being used in serialization
            }
        }
         */
        return label;
    }

    /**
     * Set label for french language.
     */
    public void setLabelFr(String label) {
        setLabel(LANG_FR, label);
    }

    private void setLabel(String language, String label) {
        LocalizedCategoryLabel target = null;
        /*
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
         */
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

    public Integer getType() {
        // return type;
        return null;
    }

    public void setType(Integer type) {
        // this.type = type;
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

    @JsonIgnore
    public String getNameEnFr() {
        String frLabel = getLocalizedLabel(Constants.LANG_FR);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(frLabel)) {
            sb.append(frLabel);
            if (StringUtils.isNotBlank(label)) {
                sb.append(" / ");
            }
        }
        if (StringUtils.isNotBlank(label)) {
            sb.append(label);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Category o) {
        return NATURAL.compare(this, o);
    }
}
