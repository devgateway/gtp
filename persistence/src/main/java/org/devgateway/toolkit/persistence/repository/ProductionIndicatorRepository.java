package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.dto.GisDTOProduction;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface ProductionIndicatorRepository extends AuditedEntityRepository<Production>,
        GisIndicatorRegion<GisDTOProduction>, GisIndicatorDepartment<GisDTOProduction> {

    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, r.code, "
            + "avg(p.production) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOProduction> findAllGisByRegion();

    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, t.code, "
            + "avg(p.production) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOProduction> findAllGisByDepartment();

}
