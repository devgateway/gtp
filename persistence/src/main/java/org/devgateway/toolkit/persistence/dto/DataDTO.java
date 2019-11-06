package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.Data;

public class DataDTO {

    private long id;
    private int year;

    public DataDTO(Data data) {
        this.id = data.getId();
        this.year = data.getYear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
