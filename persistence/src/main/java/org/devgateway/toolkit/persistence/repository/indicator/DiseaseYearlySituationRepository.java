package org.devgateway.toolkit.persistence.repository.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface DiseaseYearlySituationRepository extends BaseJpaRepository<DiseaseYearlySituation, Long> {

    @CacheHibernateQueryResult
    boolean existsByYear(Integer year);

    @CacheHibernateQueryResult
    @Query("select distinct yd.year "
            + "from DiseaseYearlySituation yd "
            + "join yd.quantities q "
            + "group by yd.year "
            + "having count(q.id) > 0")
    List<Integer> findYearsWithQuantities();

    @CacheHibernateQueryResult
    @Query("select max(q.month) "
            + "from DiseaseYearlySituation yd "
            + "join yd.quantities q "
            + "where yd.year = :year")
    Month findLastMonthWithQuantities(Integer year);

    @CacheHibernateQueryResult
    @Query("select q.disease.id, q.disease.label, sum(q.quantity) "
            + "from DiseaseYearlySituation yd "
            + "join yd.quantities q "
            + "where yd.year = :year "
            + "and q.month = :month "
            + "group by q.disease.id, q.disease.label "
            + "order by sum(q.quantity) desc, q.disease.label asc ")
    Page<Object[]> getDiseaseWithMaximumQuantity(Integer year, Month month, Pageable pageable);

    @CacheHibernateQueryResult
    @Query("select q "
            + "from DiseaseYearlySituation yd "
            + "join yd.quantities q "
            + "where yd.year = :year "
            + "and q.disease.id = :diseaseId")
    List<DiseaseQuantity> findQuantities(Integer year, Long diseaseId);
}
