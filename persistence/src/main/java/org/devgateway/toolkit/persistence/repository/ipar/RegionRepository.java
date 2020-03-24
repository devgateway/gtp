package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Region;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional
public interface RegionRepository extends BaseJpaRepository<Region, Long> {

    @Query("select r from Region r where lower(r.name) like %:name%")
    Region findByName(@Param("name") String name);

    @Query("select r from Region r where lower(r.code) like %:code%")
    Region findByCode(@Param("code") String code);
}
