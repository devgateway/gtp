package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface GisSettingsRepository extends BaseJpaRepository<GisSettings, Long> {

}
