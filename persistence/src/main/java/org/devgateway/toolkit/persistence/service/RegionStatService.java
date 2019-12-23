package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionStat;

import java.util.List;

public interface RegionStatService extends BaseJpaService<RegionStat> {

    List<RegionStat> findPopulation();
}
