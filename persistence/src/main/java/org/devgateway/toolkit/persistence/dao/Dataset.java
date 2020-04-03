package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Daniel Oliva
 */
@Entity
public class Dataset extends AbstractAuditableEntity implements Serializable, Labelable, Fileable {

    public static final String OUI_YES = "Oui/Yes";
    public static final String NON_NO = "Non/No";
    private String label;

    private String source;

    @Column(length = 1024)
    private String metadata;

    private Integer year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "dataset", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Data> data;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FileMetadata> fileMetadata;

    private boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person uploadedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person approvedBy;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private String dtype;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getLabel(String lang) {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Collection<Data> getData() {
        return data;
    }

    public void setData(Collection<Data> data) {
        this.data = data;
    }

    public Set<FileMetadata> getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(Set<FileMetadata> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Person getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Person uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Person getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Person approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return label;
     }

    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public Set<FileMetadata> getFiles() {
        return fileMetadata;
    }

    @Override
    public void setFiles(final Set<FileMetadata> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonIgnore
    public String getApprovedStr() {
        return approved ? OUI_YES : NON_NO;
    }
}
