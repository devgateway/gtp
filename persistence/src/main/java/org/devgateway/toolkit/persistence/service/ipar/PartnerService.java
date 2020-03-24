package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Partner;
import org.devgateway.toolkit.persistence.dto.ipar.PartnerDTO;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface PartnerService extends BaseJpaService<Partner> {

    List<PartnerDTO> findPartnerOrdered(String lang);

    int countByName(String name);
}
