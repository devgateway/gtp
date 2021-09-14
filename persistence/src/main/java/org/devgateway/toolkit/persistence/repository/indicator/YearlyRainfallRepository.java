package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface YearlyRainfallRepository extends BaseJpaRepository<YearlyRainfall, Long> {

    boolean existsByYear(Integer year);

    @CacheHibernateQueryResult
    @Query("select distinct sdr.pluviometricPost "
            + "from YearlyRainfall yr "
            + "join yr.stationDecadalRainfalls sdr "
            + "where yr.formStatus = 'PUBLISHED'"
            + "order by sdr.pluviometricPost.label")
    List<PluviometricPost> findPluviometricPostsWithData();

    @CacheHibernateQueryResult
    @Query("select distinct year "
            + "from YearlyRainfall "
            + "where formStatus = 'PUBLISHED' "
            + "and year >= :minYear")
    List<Integer> findYearsWithData(Integer minYear);

    @CacheHibernateQueryResult
    List<YearlyRainfall> findByFormStatusAndYearIn(FormStatus status, Collection<Integer> years);

    @CacheHibernateQueryResult
    YearlyRainfall findByYear(Integer year);
}
