package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
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
    List<DecadalRainfall> findByFormStatusAndYearIn(FormStatus status, Collection<Integer> years);
}
