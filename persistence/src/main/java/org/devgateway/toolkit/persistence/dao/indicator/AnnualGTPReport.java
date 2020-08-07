package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnore
    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileMetadata> uploads = new HashSet<>();

    public AnnualGTPReport() {
    }

    public AnnualGTPReport(Long id, Integer year) {
        this(year);
        setId(id);
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
