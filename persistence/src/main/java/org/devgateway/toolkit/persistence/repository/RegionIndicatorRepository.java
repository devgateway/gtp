package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface RegionIndicatorRepository extends BaseJpaRepository<RegionIndicator, Long> {

    @Query("select r "
            + "from RegionIndicator r "
            + "where r.approved = true and r.fakeIndicatorFlag = false")
    List<RegionIndicator> findAllApprovedNotFake();

    @Query("select r "
            + "from RegionIndicator r "
            + "where r.fakeIndicatorFlag = true")
    List<RegionIndicator> findAllFake();
}
