package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Market extends Data implements Serializable {
    private static final long serialVersionUID = -3339250112046118104L;

    private String region;
    private String department;
    private String market;
    private Date date;
    private Double milletQuantity;
    private Double milletSellPrice;
    private Double milletDetailBuyPrice;
    private Double milletWholesaleBuyPrice;
    private Double cornQuantity;
    private Double cornSellPrice;
    private Double cornDetailBuyPrice;
    private Double cornWholesaleBuyPrice;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMilletQuantity() {
        return milletQuantity;
    }

    public void setMilletQuantity(Double milletQuantity) {
        this.milletQuantity = milletQuantity;
    }

    public Double getMilletSellPrice() {
        return milletSellPrice;
    }

    public void setMilletSellPrice(Double milletSellPrice) {
        this.milletSellPrice = milletSellPrice;
    }

    public Double getMilletDetailBuyPrice() {
        return milletDetailBuyPrice;
    }

    public void setMilletDetailBuyPrice(Double milletDetailBuyPrice) {
        this.milletDetailBuyPrice = milletDetailBuyPrice;
    }

    public Double getMilletWholesaleBuyPrice() {
        return milletWholesaleBuyPrice;
    }

    public void setMilletWholesaleBuyPrice(Double milletWholesaleBuyPrice) {
        this.milletWholesaleBuyPrice = milletWholesaleBuyPrice;
    }

    public Double getCornQuantity() {
        return cornQuantity;
    }

    public void setCornQuantity(Double cornQuantity) {
        this.cornQuantity = cornQuantity;
    }

    public Double getCornSellPrice() {
        return cornSellPrice;
    }

    public void setCornSellPrice(Double cornSellPrice) {
        this.cornSellPrice = cornSellPrice;
    }

    public Double getCornDetailBuyPrice() {
        return cornDetailBuyPrice;
    }

    public void setCornDetailBuyPrice(Double cornDetailBuyPrice) {
        this.cornDetailBuyPrice = cornDetailBuyPrice;
    }

    public Double getCornWholesaleBuyPrice() {
        return cornWholesaleBuyPrice;
    }

    public void setCornWholesaleBuyPrice(Double cornWholesaleBuyPrice) {
        this.cornWholesaleBuyPrice = cornWholesaleBuyPrice;
    }
}
