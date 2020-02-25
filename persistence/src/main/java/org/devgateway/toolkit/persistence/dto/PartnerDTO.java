package org.devgateway.toolkit.persistence.dto;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Partner;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;


public class PartnerDTO {

    private Long id;

    private String name;

    private String url;

    private String contactInfo;

    private String description;

    private long groupId;

    private String groupType;

    private byte[] base64;

    public PartnerDTO(final Partner partner, final String lang) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.url = partner.getUrl();
        this.contactInfo = partner.getContactInfo();
        this.groupId = partner.getGroupType().getId();
        if (LANG_FR.equalsIgnoreCase(lang)) {
            this.description = partner.getDescriptionFr();
            this.groupType = partner.getGroupType().getLabelFr();
        } else {
            this.description = StringUtils.isNotBlank(partner.getDescription()) ? partner.getDescription()
                    : partner.getDescriptionFr();
            this.groupType = partner.getGroupType().getLabel();
        }

        this.base64 = partner.getBase64();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }
}
