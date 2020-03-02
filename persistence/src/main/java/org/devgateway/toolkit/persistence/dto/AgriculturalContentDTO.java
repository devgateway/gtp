package org.devgateway.toolkit.persistence.dto;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent;

import java.util.Date;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

/**
 * Created by Daniel Oliva
 */
public class AgriculturalContentDTO {

    private String title;

    private String description;

    private Date publicationDate;

    private long contentTypeId;

    private String contentType;

    private String link;

    private Long fileId;

    public AgriculturalContentDTO(AgriculturalContent ac, String lang) {
        if (LANG_FR.equalsIgnoreCase(lang)) {
            this.title = ac.getTitleFr();
            this.description = ac.getDescriptionFr();
            this.contentType = ac.getContentType().getLabel(LANG_FR);
        } else {
            this.title = StringUtils.isNotBlank(ac.getTitle()) ? ac.getTitle() : ac.getTitleFr();
            this.description = ac.getDescription();
            this.contentType = ac.getContentType().getLabel();
        }
        this.contentTypeId = ac.getContentType().getId();
        this.publicationDate = ac.getPublicationDate();
        this.link = ac.getLink();
        if (!ac.getFileMetadata().isEmpty()) {
            this.fileId = ac.getFileMetadata().iterator().next().getId();
        }
    }

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

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(long contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
