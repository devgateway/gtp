package org.devgateway.toolkit.persistence.dao.menu;

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
    public static final int LOGO_URL_MAX_LENGTH = 2048;
    public static final FileSize MAX_LOGO_SIZE = new FileSize(100, FileSizeUnit.KB);

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> logo = new HashSet<>();

    @Column(length = LOGO_URL_MAX_LENGTH)
    private String logoUrl;

    @Column(length = SEARCH_URL_MAX_LENGTH)
    private String searchUrl;

    private boolean isSearchUrlEnabled = true;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToOne(cascade = CascadeType.ALL)
    private MenuGroup menu;

    public CNSCHeader() {
    }

    public CNSCHeader(Long id, String searchUrl) {
        setId(id);
        this.searchUrl = searchUrl;
    }


    public Set<FileMetadata> getLogo() {
        return logo;
    }

    public void setLogo(Set<FileMetadata> logo) {
        this.logo = logo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    @JsonIgnore
    public void setSearchUrlEnabled(boolean searchUrlEnabled) {
        isSearchUrlEnabled = searchUrlEnabled;
    }

    public MenuGroup getMenu() {
        return menu;
    }

    public void setMenu(MenuGroup menu) {
        this.menu = menu;
    }

    @JsonIgnore
    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
