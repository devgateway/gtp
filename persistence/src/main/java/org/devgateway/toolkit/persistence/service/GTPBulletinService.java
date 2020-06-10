package org.devgateway.toolkit.persistence.service;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.GTPBulletin;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPBulletinService extends BaseJpaService<GTPBulletin> {

    void generate();

    List<Integer> findYears();
}
