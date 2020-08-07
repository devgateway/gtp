package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.location.Department;
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
import java.util.HashSet;
import java.util.Set;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"year", "department_id"}, name = "uk_year_dep"))
public class AnnualGTPReport extends AbstractAuditableEntity {
    private static final long serialVersionUID = 4192797393140259974L;

    @NotNull
    private Integer year;

    @ManyToOne
    private Department department;

    @JsonIgnore
    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileMetadata> uploads = new HashSet<>();

    public AnnualGTPReport() {
    }

    public AnnualGTPReport(Long id, Integer year, Department department) {
        this(year, department);
        setId(id);
    }

    public AnnualGTPReport(Integer year, Department department) {
        this.year = year;
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<FileMetadata> getUploads() {
        return uploads;
    }

    public void setUploads(Set<FileMetadata> uploads) {
        this.uploads = uploads;
    }

    @JsonIgnore
    public FileMetadata getUpload() {
        return  uploads.isEmpty() ? null : uploads.iterator().next();
    }

    @Override
    @JsonIgnore
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
