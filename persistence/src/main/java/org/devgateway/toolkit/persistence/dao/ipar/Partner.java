package org.devgateway.toolkit.persistence.dao.ipar;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.ipar.categories.PartnerGroup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * @author Octavian Ciubotaru
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Entity
 @Audited
@JsonIgnoreProperties({"new", "groupType", "logo"})
public class Partner extends AbstractAuditableEntity implements Serializable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1024)
    private String url;

    @Column(length = 1024)
    private String contactInfo;

    @Column(length = DBConstants.MAX_DEFAULT_TEXT_LENGTH)
    private String description;

    @Column(length = DBConstants.MAX_DEFAULT_TEXT_LENGTH)
    private String descriptionFr;

    @ManyToOne(optional = false)
    private PartnerGroup groupType;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FileMetadata> logo;

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartnerGroup getGroupType() {
        return groupType;
    }

    public void setGroupType(PartnerGroup groupType) {
        this.groupType = groupType;
    }

    public Set<FileMetadata> getLogo() {
        return logo;
    }

    public void setLogo(Set<FileMetadata> logo) {
        this.logo = logo;
    }

    @JsonIgnore
    public String getReducedUrl() {
        String ret;
        if (url != null && url.length() > 30) {
            ret = url.substring(0, 29) + "...";
        } else {
            ret = url;
        }
        return ret;
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

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    @JsonProperty("base64")
    public byte[] getBase64() {
        byte[] ret = null;
        if (logo != null && !logo.isEmpty() && logo.iterator().hasNext()) {
            ret = logo.iterator().next().getContent().getBytes();
        }
        return ret;
    }

    @JsonProperty("group")
    public String getGroup() {
        String ret = null;
        if (groupType != null) {
            ret = groupType.getLabel();
        }
        return ret;
    }

    @JsonProperty("groupFr")
    public String getGroupFr() {
        String ret = null;
        if (groupType != null) {
            ret = groupType.getLabelFr();
        }
        return ret;
    }
}
