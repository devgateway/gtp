package org.devgateway.toolkit.persistence.dto.rainfallMap;

import org.devgateway.toolkit.persistence.dao.Decadal;

import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class RainMapFilter {

    private final Integer year;

    private final Month month;

    private final Decadal decadal;

    public RainMapFilter(Integer year, Month month, Decadal decadal) {
        this.year = year;
        this.month = month;
        this.decadal = decadal;
    }

    public Integer getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }
}
