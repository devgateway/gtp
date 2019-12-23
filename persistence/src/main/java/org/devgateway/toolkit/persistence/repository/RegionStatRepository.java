package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.RegionStat;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface RegionStatRepository extends BaseJpaRepository<RegionStat, Long> {

    @Query("select distinct r "
            + "from RegionStat r "
            + "join r.statistic as s "
            + "where (lower(s.label) like 'population')")
    List<RegionStat> findPopulation();

}
