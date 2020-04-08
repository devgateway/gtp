package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.RegionIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
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

    @Transactional
    @Modifying
    @Query("delete from RegionIndicator r "
            + "where r.fakeIndicatorFlag = true")
    void deleteFake();
}
