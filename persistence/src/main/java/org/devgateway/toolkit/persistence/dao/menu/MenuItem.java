package org.devgateway.toolkit.persistence.dao.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Nadejda Mandrescu
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorColumn(length = MenuItem.DTYPE_COLUMN_LENGTH)
@JsonIgnoreProperties({"new"})
@Table(indexes = {@Index(columnList = "DTYPE")})
public class MenuItem extends AbstractAuditableEntity {
    private static final long serialVersionUID = -302555825635299278L;

    static final int DTYPE_COLUMN_LENGTH = 50;

    public static final int LABEL_MAX_LENGTH = 100;
    public static final int URL_MAX_LENGTH = 2048;

    @Column(length = LABEL_MAX_LENGTH, unique = true)
    protected String name;

    @NotNull
    @Column(length = LABEL_MAX_LENGTH)
    protected String label;

    @ManyToOne
    protected MenuGroup parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setParent(MenuGroup parent) {
        this.parent = parent;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return parent;
    }
}
