package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dto.FileSize;
import org.devgateway.toolkit.persistence.dto.FileSizeUnit;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Octavian Ciubotaru
 */
@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GTPMember extends AbstractAuditableEntity {
    private static final long serialVersionUID = -1441239458746425521L;

    public static final int NAME_MAX_LENGTH = 85;
    public static final int DESCRIPTION_MAX_LENGTH = 550;
    public static final FileSize MAX_ICON_SIZE = new FileSize(100, FileSizeUnit.KB);

    @ExcelExport(name = "gtpMemberName", useTranslation = true)
    @NotNull
    @Column(length = NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String description;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> logo = new HashSet<>();

    private String url;

    public GTPMember() {
    }

    public GTPMember(Long id, String name, String description, String url) {
        setId(id);
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FileMetadata> getLogo() {
        return logo;
    }

    public void setLogo(Set<FileMetadata> logo) {
        this.logo = logo;
    }

    @JsonIgnore
    public FileMetadata getLogoSingle() {
        return logo.isEmpty() ? null : logo.iterator().next();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    @JsonIgnore
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
