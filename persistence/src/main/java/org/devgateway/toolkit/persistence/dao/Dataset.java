package org.devgateway.toolkit.persistence.dao;

import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Daniel Oliva
 */
@Entity
public class Dataset extends AbstractAuditableEntity implements Serializable, Labelable, Fileable {

    private String label;

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

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
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
}
