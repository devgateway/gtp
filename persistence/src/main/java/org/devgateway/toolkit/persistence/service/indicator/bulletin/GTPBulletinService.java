package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPBulletinService extends BaseJpaService<GTPBulletin> {

    void generate();

    List<Integer> findYears();

    List<GTPBulletin> findAllWithUploadsAndLocation(Long locationId);
}
