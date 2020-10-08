package org.devgateway.toolkit.persistence.dao.menu;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class MenuLeaf extends MenuItem {
    private static final long serialVersionUID = -3038414481192732735L;

    public MenuLeaf() {
    }

    public MenuLeaf(Long id, String label, Integer index, String url) {
        setId(id);
        this.name = label;
        this.label = label;
        this.index = index;
        this.url = url;
    }

    @NotNull
    @Column(length = URL_MAX_LENGTH)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
