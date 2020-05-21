package org.devgateway.toolkit.persistence.dao;

import java.util.SortedSet;

import org.devgateway.toolkit.persistence.dao.categories.RiverStation;

/**
 * @author Octavian Ciubotaru
 */
public interface IRiverStationYearlyLevels<T extends IRiverLevel> {

    RiverStation getStation();

    void setStation(RiverStation station);

    HydrologicalYear getYear();

    void setYear(HydrologicalYear year);

    SortedSet<T> getLevels();

    void addLevel(T level);
}
