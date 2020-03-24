package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.GisSettingsDescription;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NoRepositoryBean
@Transactional
public interface GisSettingsDescriptionRepository extends BaseJpaRepository<GisSettingsDescription, Long> {

    @Query("select g from GisSettingsDescription g left join fetch g.gisIndicator d where d.type = :typeId")
    Optional<GisSettingsDescription> findByType(@Param("typeId") int typeId);
}
