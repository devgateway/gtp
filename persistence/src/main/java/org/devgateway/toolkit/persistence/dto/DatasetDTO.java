package org.devgateway.toolkit.persistence.dto;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.MicrodataLink;
import org.devgateway.toolkit.persistence.dao.ipar.categories.DatasetType;

import java.time.LocalDateTime;

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;
import static org.devgateway.toolkit.persistence.util.Constants.NOT_AVAILABLE;


/**
 * @author Daniel Oliva
 */
public class DatasetDTO {

    private Long id;
    private String organization;
    private String title;
    private String description;
    private String link;
    private String source;
    private String metadata;
    private String creator;
    private LocalDateTime createdDate;
    private Long fileId;
    private String type;
    private Long typeId;
    private Integer year;

    public DatasetDTO(final Dataset dataset, final DatasetType type, String lang) {
        boolean isFr = StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR);
        this.id = dataset.getId();
        this.title = dataset.getLabel();
        this.source = dataset.getSource() != null ? dataset.getSource() : EMPTY_STRING;
        this.organization = dataset.getOrganization() != null ? dataset.getOrganization().getLabel() : NOT_AVAILABLE;
        this.creator = dataset.getUploadedBy() != null ? dataset.getUploadedBy().getFirstName() + " "
                + dataset.getUploadedBy().getLastName() : NOT_AVAILABLE;
        this.createdDate = dataset.getCreatedDate() != null ? dataset.getCreatedDate().get().toLocalDateTime() : null;
        this.year = dataset.getYear();
        this.fileId = dataset.getFileMetadata() != null ? dataset.getFileMetadata().iterator().next().getId() : null;
        this.type = isFr ? type.getLabelFr() : type.getLabel();
        this.typeId = type.getId();
        this.metadata = dataset.getMetadata();
    }

    public DatasetDTO(final MicrodataLink data, String lang) {
        boolean isFr = StringUtils.isNotBlank(lang) && lang.equalsIgnoreCase(LANG_FR);
        this.title = isFr || StringUtils.isBlank(data.getTitle()) ? data.getTitleFr() : data.getTitle();
        this.description = isFr ? data.getDescriptionFr() : data.getDescription();
        this.link = data.getLink();
        this.organization = data.getOrganization() != null ? data.getOrganization().getLabel() : NOT_AVAILABLE;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
