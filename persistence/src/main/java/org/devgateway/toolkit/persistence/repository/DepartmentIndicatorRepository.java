package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface DepartmentIndicatorRepository extends BaseJpaRepository<DepartmentIndicator, Long> {

    @Query("select r "
            + "from DepartmentIndicator r "
            + "where r.approved = true and r.fakeIndicatorFlag = false")
    List<DepartmentIndicator> findAllApprovedNotFake();

    @Query("select r "
            + "from DepartmentIndicator r "
            + "where r.fakeIndicatorFlag = true")
    List<DepartmentIndicator> findAllFake();

    @Transactional
    @Modifying
    @Query("delete from DepartmentIndicator r "
            + "where r.fakeIndicatorFlag = true")
    void deleteFake();
}
