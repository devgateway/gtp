package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Dataset;

import java.time.LocalDateTime;


/**
 * @author Daniel Oliva
 */
public class DatasetDTO {

    public static final String NOT_AVAILABLE = "N/A";
    private String organization;
    private String title;
    private String source;
    private String creator;
    private LocalDateTime createdDate;
    private Long fileId;
    private String type;

    public DatasetDTO(final Dataset dataset) {
        this.title = dataset.getLabel();
        this.source = dataset.getSource();
        this.organization = dataset.getOrganization() != null ? dataset.getOrganization().getLabel() : NOT_AVAILABLE;
        this.creator = dataset.getCreatedBy() != null ? dataset.getCreatedBy().get() : NOT_AVAILABLE;
        this.createdDate = dataset.getCreatedDate() != null ? dataset.getCreatedDate().get().toLocalDateTime() : null;
        this.fileId = dataset.getFileMetadata() != null ? dataset.getFileMetadata().iterator().next().getId() : null;
        this.type = dataset.getDtype();
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
}
