package org.devgateway.toolkit.persistence.dao.ipar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.validator.Identifier;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@javax.persistence.Table(name = "md_table")
public class Table extends AbstractAuditableEntity {

    /**
     * Name used for database table.
     */
    @Identifier
    @NotNull
    @javax.persistence.Column(nullable = false, unique = true)
    private String name;

    /**
     * User friendly name.
     */
    private String displayName;

    /**
     * Used by system? Cannot be deleted because the structure must not change. For example it could used in
     * indicator pages.
     * <p>Alternatively drop this column and do not allow deleting a table that is used at least one of the charts?</p>
     */
    @javax.persistence.Column(nullable = false)
    private Boolean system = false;

    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private DataSource dataSource;

    @Valid
    @Size(min = 1)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderColumn(name = "index")
    private List<Column> columns = new ArrayList<>();

    public Table() {
    }

    public Table(String name, DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        column.setParent(this);
        columns.add(column);
    }
}
