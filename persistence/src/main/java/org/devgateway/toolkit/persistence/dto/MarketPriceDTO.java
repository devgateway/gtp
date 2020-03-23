package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.ipar.MarketPrice;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;

import java.time.LocalDate;

public class MarketPriceDTO extends DataDTO {

    @ExcelExport
    private String cropType;

    @ExcelExport
    private String market;

    @ExcelExport
    private LocalDate date;

    @ExcelExport
    private Double quantity;

    @ExcelExport
    private Double sellPrice;

    @ExcelExport
    private Double detailBuyPrice;

    @ExcelExport
    private Double wholesaleBuyPrice;

    public MarketPriceDTO(MarketPrice data, final String lang) {
        super(data, lang);
        this.cropType = getStr(data.getCropType());
        this.market = getStr(data.getMarket());
        this.date = data.getDate();
        this.quantity = data.getQuantity();
        this.sellPrice = data.getSellPrice();
        this.detailBuyPrice = data.getDetailBuyPrice();
        this.wholesaleBuyPrice = data.getWholesaleBuyPrice();
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
}
