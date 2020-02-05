package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.dto.ProductionGisDTO;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface ProductionIndicatorRepository extends AuditedEntityRepository<Production> {

    @Query("select new org.devgateway.toolkit.persistence.dto.ProductionGisDTO(p.year, r.code, "
            + "avg(p.production) as value, c.label as crop, l.label as cropFr) from Production p "
            + "join p.region as r "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "group by p.year, c.label, l.label, r.code "
            + "order by p.year, c.label, l.label, r.code")
    List<ProductionGisDTO> findAllProductionGis();

}
