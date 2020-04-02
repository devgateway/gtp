package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.DepartmentStat;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface DepartmentStatRepository extends BaseJpaRepository<DepartmentStat, Long> {

}