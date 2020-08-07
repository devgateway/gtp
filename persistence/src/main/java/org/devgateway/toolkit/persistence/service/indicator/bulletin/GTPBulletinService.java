package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPBulletinService extends BaseJpaService<GTPBulletin> {

    void generate();

    List<Integer> findYears();

    List<GTPBulletin> findAllWithUploads();
}
