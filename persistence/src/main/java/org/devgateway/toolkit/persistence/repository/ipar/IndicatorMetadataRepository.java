package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface IndicatorMetadataRepository extends BaseJpaRepository<IndicatorMetadata, Long> {

    @Query("select im from IndicatorMetadata im inner join im.indicator i where i.type = :type order by im.id desc")
    List<IndicatorMetadata> findByIndicatorType(@Param("type") Integer type);
}
