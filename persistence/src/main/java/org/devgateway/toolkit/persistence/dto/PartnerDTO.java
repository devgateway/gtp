package org.devgateway.toolkit.persistence.dto;


import org.devgateway.toolkit.persistence.dao.Partner;

public class PartnerDTO {

    public static final String LANG_FR = "fr";
    private String name;

    private String url;

    private String contactInfo;

    private String description;

    private String groupType;

    private byte[] base64;

    public PartnerDTO(final Partner partner, final String lang) {
        this.name = partner.getName();
        this.url = partner.getUrl();
        this.contactInfo = partner.getContactInfo();
        if (LANG_FR.equalsIgnoreCase(lang)) {
            this.description = partner.getDescriptionFr();
            this.groupType = partner.getGroupType().getLabelFr();
        } else {
            this.description = partner.getDescription();
            this.groupType = partner.getGroupType().getLabel();
        }

        this.base64 = partner.getBase64();
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