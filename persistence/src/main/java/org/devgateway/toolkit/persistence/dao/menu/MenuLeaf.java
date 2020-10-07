package org.devgateway.toolkit.persistence.dao.menu;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
public class MenuLeaf extends MenuItem {
    private static final long serialVersionUID = -3038414481192732735L;

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
