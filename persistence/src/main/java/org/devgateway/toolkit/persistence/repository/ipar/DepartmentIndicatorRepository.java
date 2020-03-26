package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.DepartmentIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
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
}
