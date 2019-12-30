package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"id", "new"})
public class WebContent extends AbstractAuditableEntity implements Serializable {

    private String title;

    private String subtitle;

    private String titleFr;

    private String subtitleFr;

    @Column(length = 1024)
    private String link;

    @Column(length = DBConstants.MAX_DEFAULT_TEXT_LENGTH)
    private String summernote;

    @Column(length = DBConstants.MAX_DEFAULT_TEXT_LENGTH)
    private String summernoteFr;

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

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getSubtitleFr() {
        return subtitleFr;
    }

    public void setSubtitleFr(String subtitleFr) {
        this.subtitleFr = subtitleFr;
    }

    public String getSummernoteFr() {
        return summernoteFr;
    }

    public void setSummernoteFr(String summernoteFr) {
        this.summernoteFr = summernoteFr;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
