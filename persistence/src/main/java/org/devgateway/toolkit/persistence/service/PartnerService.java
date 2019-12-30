package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Partner;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface PartnerService extends BaseJpaService<Partner> {

    List<Partner> findPartnerOrdered();
}
