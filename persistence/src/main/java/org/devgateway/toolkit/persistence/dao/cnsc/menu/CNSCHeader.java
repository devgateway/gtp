package org.devgateway.toolkit.persistence.dao.cnsc.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dto.FileSize;
import org.devgateway.toolkit.persistence.dto.FileSizeUnit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
public class CNSCHeader extends AbstractAuditableEntity {
    private static final long serialVersionUID = 5005996829926227575L;

    public static final int SEARCH_URL_MAX_LENGTH = 1000;
    public static final FileSize MAX_LOGO_SIZE = new FileSize(100, FileSizeUnit.KB);

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> logo = new HashSet<>();

    @Column(length = SEARCH_URL_MAX_LENGTH)
    private String searchUrl;

    private boolean isSearchUrlEnabled = true;

    @OneToOne(cascade = CascadeType.ALL)
    private CNSCMenuGroup menu;

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

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public boolean isSearchUrlEnabled() {
        return isSearchUrlEnabled;
    }

    public void setSearchUrlEnabled(boolean searchUrlEnabled) {
        isSearchUrlEnabled = searchUrlEnabled;
    }

    public CNSCMenuGroup getMenu() {
        return menu;
    }

    public void setMenu(CNSCMenuGroup menu) {
        this.menu = menu;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
