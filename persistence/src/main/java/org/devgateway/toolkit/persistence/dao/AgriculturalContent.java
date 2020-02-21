package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.devgateway.toolkit.persistence.dao.categories.ContentType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonPropertyOrder({"id", "title", "description", "link", "fileId"})
@JsonIgnoreProperties({"new"})
public class AgriculturalContent extends AbstractAuditableEntity implements Serializable {

    private String title;

    private String description;

    private String titleFr;

    private String descriptionFr;

    private Date publicationDate;

    @Column(length = 1024)
    private String link;

    @JsonIgnore
    @ManyToOne(optional = false)
    private ContentType contentType;

    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FileMetadata> fileMetadata;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Set<FileMetadata> getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(Set<FileMetadata> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    @JsonIgnore
    public String getReducedTitle() {
        return getReduced(title);
    }

    @JsonIgnore
    public String getReducedTitleFr() {
        return getReduced(titleFr);
    }

    @JsonIgnore
    public String getContentTypeFr() {
        return contentType.getLabelFr();
    }

    @JsonIgnore
    public String getPublicationDateFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(publicationDate);
    }

    @JsonIgnore
    public String getReduced(String prop) {
        String ret;
        if (prop != null && prop.length() > 30) {
            ret = prop.substring(0, 29) + "...";
        } else {
            ret = prop;
        }
        return ret;
    }

    @JsonIgnore
    public String getReducedDescription() {
        String ret;
        if (description != null && description.length() > 50) {
            ret = description.substring(0, 49) + "...";
        } else {
            ret = description;
        }
        return ret;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
