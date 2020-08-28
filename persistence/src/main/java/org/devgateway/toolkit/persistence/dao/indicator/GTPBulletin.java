package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.jackson.DepartmentIdNullableSerializer;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"year", "month", "decadal", "department_id"}))
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GTPBulletin extends AbstractAuditableEntity {
    private static final long serialVersionUID = -1781769365489621450L;

    public static final List<Month> MONTHS =
            ImmutableList.of(Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER);

    @NotNull
    private Integer year;

    @NotNull
    private Month month;

    @NotNull
    private Decadal decadal;

    @ManyToOne(optional = true)
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty(value = "locationId")
    @JsonSerialize(using = DepartmentIdNullableSerializer.class)
    private Department department;

    @JsonIgnore
    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileMetadata> uploads = new HashSet<>();

    public GTPBulletin() {
    }

    public GTPBulletin(Long id, Integer year, Month month, Decadal decadal, Department department) {
        this(year, month, decadal, department);
        setId(id);
    }

    public GTPBulletin(Integer year, Month month, Decadal decadal, Department department) {
        this.year = year;
        this.month = month;
        this.decadal = decadal;
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public void setDecadal(Decadal decadal) {
        this.decadal = decadal;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonIgnore
    public FileMetadata getUpload() {
        return  uploads.isEmpty() ? null : uploads.iterator().next();
    }

    public Set<FileMetadata> getUploads() {
        return uploads;
    }

    public void setUploads(Set<FileMetadata> uploads) {
        this.uploads = uploads;
    }

    @Override
    @JsonIgnore
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
