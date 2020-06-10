package org.devgateway.toolkit.persistence.service;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPBulletinService extends BaseJpaService<AnnualGTPBulletin> {

    void generate();

    List<Integer> findYears();
}
