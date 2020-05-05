package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface DecadalRainfallRepository extends BaseJpaRepository<DecadalRainfall, Long> {

    boolean existsByYear(Integer year);

    @CacheHibernateQueryResult
    @Query("select distinct prf.pluviometricPost.id "
            + "from DecadalRainfall drf "
            + "join drf.postRainfalls prf "
            + "where drf.formStatus = 'PUBLISHED'")
    List<Long> findPluviometricPostsWithData();

    @CacheHibernateQueryResult
    @Query("select distinct year "
            + "from DecadalRainfall "
            + "where formStatus = 'PUBLISHED'")
    List<Integer> findYearsWithData();

    @CacheHibernateQueryResult
    @Query("select new org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel("
            + "drf.year, drf.month, drf.decadal, sum(rf.rain)) "
            + "from DecadalRainfall drf "
            + "join drf.postRainfalls prf "
            + "join prf.rainfalls rf "
            + "where drf.formStatus = 'PUBLISHED' "
            + "and drf.year in :years "
            + "and prf.pluviometricPost.id = :pluviometricPostId "
            + "group by drf.year, drf.month, drf.decadal "
            + "order by drf.year, drf.month, drf.decadal")
    List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId);

    @CacheHibernateQueryResult
    @Query("select new org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain("
            + "drf.year, drf.month, drf.decadal, count(rf.id)) "
            + "from DecadalRainfall drf "
            + "join drf.postRainfalls prf "
            + "join prf.rainfalls rf "
            + "where drf.formStatus = 'PUBLISHED' "
            + "and drf.year = :year "
            + "and prf.pluviometricPost.id = :pluviometricPostId "
            + "and rf.rain >= 0.1 "
            + "group by drf.year, drf.month, drf.decadal "
            + "order by drf.month, drf.decadal")
    List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId);
}
