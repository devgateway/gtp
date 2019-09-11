package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String department;
    private String market;
    private Double quantity;
    private Double sellPrice;
    private Double detailBuyPrice;
    private Double wholesaleBuyPrice;

    public Market() {
    }

    public Market(String region, String department, String market, Date date, String crop) {
        this.region = region;
        this.department = department;
        this.market = market;
        this.crop = crop;
        this.date = date;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getDetailBuyPrice() {
        return detailBuyPrice;
    }

    public void setDetailBuyPrice(Double detailBuyPrice) {
        this.detailBuyPrice = detailBuyPrice;
    }

    public Double getWholesaleBuyPrice() {
        return wholesaleBuyPrice;
    }

    public void setWholesaleBuyPrice(Double wholesaleBuyPrice) {
        this.wholesaleBuyPrice = wholesaleBuyPrice;
    }

    @JsonIgnore
    public boolean isValid() {
        boolean ret = true;
        if (quantity == null && sellPrice == null && detailBuyPrice == null && wholesaleBuyPrice == null) {
            ret = false;
        }
        return ret;
    }
}
