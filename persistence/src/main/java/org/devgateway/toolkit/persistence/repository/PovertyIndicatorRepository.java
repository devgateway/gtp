package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dto.PovertyGisDTO;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface PovertyIndicatorRepository extends AuditedEntityRepository<PovertyIndicator> {

    @Query("select new org.devgateway.toolkit.persistence.dto.PovertyGisDTO(p.year, r.code, "
            + "avg(p.povertyScore) as value, d.source) from PovertyIndicator p "
            + "join p.region as r "
            + "join p.dataset as d "
            + "group by p.year, r.code, d.source "
            + "order by p.year, r.code, d.source")
    List<PovertyGisDTO> findAllPovertyGis();

}
