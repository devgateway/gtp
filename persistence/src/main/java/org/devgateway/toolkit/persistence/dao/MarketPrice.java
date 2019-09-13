package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonPropertyOrder({"region_id", "department", "market", "crop", "date", "quantity", "sellPrice", "detailBuyPrice",
        "wholesaleBuyPrice"})
public class MarketPrice extends Data implements Serializable {
    private static final long serialVersionUID = -3339250112046118104L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;
    private String department;
    private String market;
    private String crop;
    private LocalDate date;
    private Double quantity;
    private Double sellPrice;
    private Double detailBuyPrice;
    private Double wholesaleBuyPrice;

    public MarketPrice() {
    }

    public Region getRegion() {
        return region;
    }

    @JsonProperty("region_id")
    public Long getRegionId() {
        if (region != null) {
            return region.getId();
        }
        return null;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
