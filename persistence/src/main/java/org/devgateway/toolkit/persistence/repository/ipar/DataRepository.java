package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Data;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface DataRepository extends BaseJpaRepository<Data, Long> {

    @Query("select distinct(d.year) from Data d where d.year is not null order by d.year asc")
    List<Integer> findDistinctYears();

}
