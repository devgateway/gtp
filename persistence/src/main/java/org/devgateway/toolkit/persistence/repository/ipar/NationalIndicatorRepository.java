package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.NationalIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface NationalIndicatorRepository extends BaseJpaRepository<NationalIndicator, Long> {

    @Query("select distinct(r) "
            + "from NationalIndicator r "
            + "join fetch r.yearValue as s ")
    List<NationalIndicator> findAll();
}
