package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.devgateway.toolkit.persistence.dto.ipar.GisDTOProduction;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
@CacheConfig(cacheNames = "productionCache")
public interface ProductionIndicatorRepository extends AuditedEntityRepository<Production> {

    @Cacheable
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
    List<GisDTOProduction> findAllGisProductionByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, t.code, "
            + "avg(p.production) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOProduction> findAllGisProductionByDepartment();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, r.code, "
            + "avg(p.surface) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOProduction> findAllGisSurfaceByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, t.code, "
            + "avg(p.surface) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOProduction> findAllGisSurfaceByDepartment();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, r.code, "
            + "avg(p.yield) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOProduction> findAllGisYieldByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOProduction(p.year, t.code, "
            + "avg(p.yield) as value, c.label as crop, l.label as cropFr, d.source) from Production p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOProduction> findAllGisYieldByDepartment();

}
