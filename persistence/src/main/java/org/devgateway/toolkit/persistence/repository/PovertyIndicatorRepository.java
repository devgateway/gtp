package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ipar.PovertyIndicator;
import org.devgateway.toolkit.persistence.dto.GisDTOPoverty;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface PovertyIndicatorRepository extends AuditedEntityRepository<PovertyIndicator> {

    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOPoverty(p.year, r.code, "
            + "avg(p.povertyScore) as value, d.source) from PovertyIndicator p "
            + "join p.region as r "
            + "join p.dataset as d "
            + "where d.approved = true "
            + "group by p.year, r.code, d.source "
            + "order by p.year, r.code, d.source")
    List<GisDTOPoverty> findAllGisDailyConsumptionByRegion();

}
