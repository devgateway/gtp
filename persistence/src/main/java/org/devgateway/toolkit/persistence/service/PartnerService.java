package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.dto.PartnerDTO;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface PartnerService extends BaseJpaService<Partner> {

    List<PartnerDTO> findPartnerOrdered(String lang);
}
