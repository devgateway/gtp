package org.devgateway.toolkit.persistence.dao.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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
public class MenuItem extends AbstractAuditableEntity implements Comparable<MenuItem> {
    private static final long serialVersionUID = -302555825635299278L;

    static final int DTYPE_COLUMN_LENGTH = 50;

    public static final int LABEL_MAX_LENGTH = 100;
    public static final int URL_MAX_LENGTH = 2048;

    @Column(length = LABEL_MAX_LENGTH, unique = true)
    protected String name;

    @NotNull
    @Column(length = LABEL_MAX_LENGTH)
    protected String label;

    protected Integer index;

    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fkMenuItemParent"))
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


    public void setParent(MenuGroup parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return true;
    }

    @JsonIgnore
    public boolean isRoot() {
        return this.getParent() == null;
    }

    @Override
    public MenuGroup getParent() {
        return parent;
    }

    @Override
    public int compareTo(MenuItem menuItem) {
        return this.getIndex().compareTo(menuItem.getIndex());
    }
}
