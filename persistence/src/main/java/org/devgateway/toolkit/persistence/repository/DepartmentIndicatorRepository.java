package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ipar.DepartmentIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
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
            + "where r.approved = true")
    List<DepartmentIndicator> findAllApproved();
}
