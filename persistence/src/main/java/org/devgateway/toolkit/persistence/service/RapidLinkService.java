package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.RapidLink;

import java.util.Optional;

/**
 * Created by Daniel Oliva
 */
public interface RapidLinkService extends BaseJpaService<RapidLink> {

    Optional<RapidLink> findByRapidLinkPositionId(Long id);

    Iterable<RapidLink> findByRapidLinkPositionIdNotNull();
}
