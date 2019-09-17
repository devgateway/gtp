package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Created by Daniel Oliva
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonPropertyOrder({"market", "cropType", "date", "quantity", "sellPrice", "detailBuyPrice", "wholesaleBuyPrice"})
public class MarketPrice extends Data {

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private Market market;

    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @PivotTableField(hideInAggregators = true, hideInDragAndDrop = true)
    @NotNull
    private CropType cropType;

    @PivotTableField(hideInAggregators = true)
    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @PivotTableField(hideInDragAndDrop = true)
    private Double quantity;

    @PivotTableField(hideInDragAndDrop = true)
    private Double sellPrice;

    @PivotTableField(hideInDragAndDrop = true)
    private Double detailBuyPrice;

    @PivotTableField(hideInDragAndDrop = true)
    private Double wholesaleBuyPrice;

    public MarketPrice() {
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
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
