package org.devgateway.toolkit.persistence.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnnualGTPReport extends AbstractAuditableEntity {

    @NotNull
    @Column(unique = true)
    private Integer year;

    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileMetadata> uploads = new HashSet<>();

    public AnnualGTPReport() {
    }

    public AnnualGTPReport(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
