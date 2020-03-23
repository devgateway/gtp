package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface DatasetRepository extends BaseJpaRepository<Dataset, Long> {

    @Query("select distinct(d.year) from Dataset d where d.year is not null order by d.year desc")
    List<Integer> findDistinctYears();

    @Query("select distinct(d.organization) from Dataset d where d.organization is not null")
    List<Organization> findDistinctOrganizations();
}
