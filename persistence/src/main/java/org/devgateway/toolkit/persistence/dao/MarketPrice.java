package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonPropertyOrder({"market", "crop", "date", "quantity", "sellPrice", "detailBuyPrice", "wholesaleBuyPrice"})
public class MarketPrice extends Data {

    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Market market;

    private String crop;
    private LocalDate date;
    private Double quantity;
    private Double sellPrice;
    private Double detailBuyPrice;
    private Double wholesaleBuyPrice;

    public MarketPrice() {
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

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
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
