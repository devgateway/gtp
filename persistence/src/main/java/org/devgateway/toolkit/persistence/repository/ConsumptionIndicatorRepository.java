package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.dto.GisDTOConsumption;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@Transactional
@CacheConfig(cacheNames = "consumptionCache")
public interface ConsumptionIndicatorRepository extends AuditedEntityRepository<Consumption> {

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, r.code, "
            + "avg(p.dailyConsumption) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOConsumption> findAllGisDailyByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, t.code, "
            + "avg(p.dailyConsumption) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOConsumption> findAllGisDailyByDepartment();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, r.code, "
            + "avg(p.weeklyConsumption) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOConsumption> findAllGisWeeklyByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, t.code, "
            + "avg(p.weeklyConsumption) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOConsumption> findAllGisWeeklyByDepartment();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, r.code, "
            + "avg(p.householdSize) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join t.region as r "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, r.code, d.source "
            + "order by p.year, c.label, l.label, r.code, d.source")
    List<GisDTOConsumption> findAllGisSizeByRegion();

    @Cacheable
    @Query("select new org.devgateway.toolkit.persistence.dto.GisDTOConsumption(p.year, t.code, "
            + "avg(p.householdSize) as value, c.label as crop, l.label as cropFr, d.source) from Consumption p "
            + "join p.department as t "
            + "join p.dataset as d "
            + "join p.cropType as c "
            + "join c.localizedLabels as l "
            + "where d.approved = true "
            + "group by p.year, c.label, l.label, t.code, d.source "
            + "order by p.year, c.label, l.label, t.code, d.source")
    List<GisDTOConsumption> findAllSizeByDepartment();

}
