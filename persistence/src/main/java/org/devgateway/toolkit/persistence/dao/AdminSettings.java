package org.devgateway.toolkit.persistence.dao;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author idobre
 * @since 6/22/16
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminSettings extends AbstractAuditableEntity {
    private static final long serialVersionUID = -1051140524022133178L;

    public static final Duration REBOOT_ALERT_DURATION = Duration.ofMinutes(10L);

    private Boolean rebootServer = false;

    private LocalDateTime rebootAlertSince;

    private Integer startingYear;

    @ManyToOne
    private RiverStation defaultRiverStation;

    @ManyToOne
    private Product defaultProduct;

    @ManyToOne
    private ProductType defaultProductType;

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public Boolean getRebootServer() {
        return rebootServer;
    }

    public void setRebootServer(final Boolean rebootServer) {
        this.rebootServer = rebootServer;
    }

    public boolean isRebootServer() {
        return Boolean.TRUE.equals(getRebootServer());
    }

    public LocalDateTime getRebootAlertSince() {
        return rebootAlertSince;
    }

    public void setRebootAlertSince(LocalDateTime rebootAlertSince) {
        this.rebootAlertSince = rebootAlertSince;
    }

    public Integer getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(Integer startingYear) {
        this.startingYear = startingYear;
    }

    public RiverStation getDefaultRiverStation() {
        return defaultRiverStation;
    }

    public void setDefaultRiverStation(RiverStation defaultRiverStation) {
        this.defaultRiverStation = defaultRiverStation;
    }

    public Product getDefaultProduct() {
        return defaultProduct;
    }

    public void setDefaultProduct(Product defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    public ProductType getDefaultProductType() {
        return defaultProductType;
    }

    public void setDefaultProductType(ProductType defaultProductType) {
        this.defaultProductType = defaultProductType;
    }
}
