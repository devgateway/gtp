package org.devgateway.toolkit.persistence.dao.ipar;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.validation.constraints.NotNull;

import org.devgateway.toolkit.persistence.dao.AbstractChildAuditableEntity;
import org.devgateway.toolkit.persistence.validator.Identifier;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
// @Entity
 @Audited
@javax.persistence.Table(name = "md_column",
        indexes = @Index(unique = true, columnList = "parent_id, name"))
public class Column extends AbstractChildAuditableEntity<Table> {

    /**
     * Name used for database column.
     */
    @Identifier
    @NotNull
    @javax.persistence.Column(nullable = false)
    private String name;

    /**
     * User friendly name.
     */
    private String displayName;

    /**
     * One of: text/bigint/double precision/timestamp
     * TODO use enum to be more strict?
     */
    @NotNull
    @javax.persistence.Column(nullable = false)
    private String type;

    /**
     * Can this column be used to for grouping purposes?
     */
    @NotNull
    @javax.persistence.Column(nullable = false)
    private Boolean groupable = false;

    /**
     * Can this column be used for filtering purposes?
     */
    @NotNull
    @javax.persistence.Column(nullable = false)
    private Boolean filterable = false;

    public Column() {
    }

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isGroupable() {
        return groupable;
    }

    public void setGroupable(boolean groupable) {
        this.groupable = groupable;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }
}
