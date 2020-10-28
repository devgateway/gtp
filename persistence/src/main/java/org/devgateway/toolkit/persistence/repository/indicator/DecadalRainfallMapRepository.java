package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface DecadalRainfallMapRepository extends BaseJpaRepository<DecadalRainfallMap, Long> {

    boolean existsByYear(Integer year);

    @CacheHibernateQueryResult
    @Query("select distinct drm.year "
            + "from DecadalRainfallMap drm "
            + "join drm.layers l "
            + "where drm.year >= :minYear")
    List<Integer> findYearsWithData(Integer minYear);

    @CacheHibernateQueryResult
    @Query("select max(drm.month) "
            + "from DecadalRainfallMap drm "
            + "join drm.layers l "
            + "where drm.year = :year")
    Month findLastMonthWithData(Integer year);

    @CacheHibernateQueryResult
    @Query("select max(drm.decadal) "
            + "from DecadalRainfallMap drm "
            + "join drm.layers l "
            + "where drm.year = :year "
            + "and drm.month = :month")
    Decadal findLastDecadalWithData(Integer year, Month month);

    DecadalRainfallMap findByYearAndMonthAndDecadal(Integer year, Month month, Decadal decadal);

}
