package org.devgateway.toolkit.persistence.dao.categories;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.lang.NonNull;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "language"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocalizedCategoryLabel extends AbstractAuditableEntity implements Serializable {

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    private Category category;

    @Column(nullable = false)
    @NotNull
    private String language;

    private String label;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return category;
    }

    @Override
    @NonNull
    public String toString() {
        return language + "=" + label;
    }
}
