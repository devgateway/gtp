package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RapidLink;

import java.util.Optional;

/**
 * Created by Daniel Oliva
 */
public interface RapidLinkService extends BaseJpaService<RapidLink> {

    Optional<RapidLink> findByRapidLinkPositionId(Long id);
}
