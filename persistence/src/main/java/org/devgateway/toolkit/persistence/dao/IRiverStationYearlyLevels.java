package org.devgateway.toolkit.persistence.dao;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.RiverStation;

/**
 * @author Octavian Ciubotaru
 */
public interface IRiverStationYearlyLevels<T extends IRiverLevel> {

    RiverStation getStation();

    void setStation(RiverStation station);

    HydrologicalYear getYear();

    void setYear(HydrologicalYear year);

    List<T> getLevels();

    void addLevel(T level);
}
