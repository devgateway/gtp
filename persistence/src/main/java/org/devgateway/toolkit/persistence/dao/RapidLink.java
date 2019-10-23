package org.devgateway.toolkit.persistence.dao;

import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class RapidLink extends AbstractAuditableEntity implements Serializable {

    private String title;

    private String subtitle;

    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private RapidLinkPosition rapidLinkPosition;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FileMetadata> fileMetadata;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<FileMetadata> getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(Set<FileMetadata> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public RapidLinkPosition getRapidLinkPosition() {
        return rapidLinkPosition;
    }

    public void setRapidLinkPosition(RapidLinkPosition rapidLinkPosition) {
        this.rapidLinkPosition = rapidLinkPosition;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
