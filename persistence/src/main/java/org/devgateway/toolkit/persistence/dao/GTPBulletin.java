package org.devgateway.toolkit.persistence.dao;

import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"year", "month", "decadal"}))
public class GTPBulletin extends AbstractAuditableEntity {

    public static final List<Month> MONTHS =
            ImmutableList.of(Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER);

    @NotNull
    private Integer year;

    @NotNull
    private Month month;

    @NotNull
    private Decadal decadal;

    @JsonIgnore
    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileMetadata> uploads = new HashSet<>();

    public GTPBulletin() {
    }

    public GTPBulletin(Long id, Integer year, Month month, Decadal decadal) {
        this(year, month, decadal);
        setId(id);
    }

    public GTPBulletin(Integer year, Month month, Decadal decadal) {
        this.year = year;
        this.month = month;
        this.decadal = decadal;
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
