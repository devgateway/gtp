package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class WebContent extends AbstractAuditableEntity implements Serializable {

    private String title;

    private String subtitle;

    private String link;

    @Column(length = DBConstants.MAX_DEFAULT_TEXT_LENGTH)
    private String summernote;

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

    public String getSummernote() {
        return summernote;
    }

    public void setSummernote(String summernote) {
        this.summernote = summernote;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
