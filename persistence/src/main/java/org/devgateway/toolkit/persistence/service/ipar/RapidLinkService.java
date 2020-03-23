package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.RapidLink;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.Optional;

/**
 * Created by Daniel Oliva
 */
public interface RapidLinkService extends BaseJpaService<RapidLink> {

    Optional<RapidLink> findByRapidLinkPositionId(Long id);

    Iterable<RapidLink> findByRapidLinkPositionIdNotNull();
}
