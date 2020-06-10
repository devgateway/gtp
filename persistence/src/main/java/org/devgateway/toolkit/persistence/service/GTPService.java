package org.devgateway.toolkit.persistence.service;

import java.util.Optional;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPService {

    GTPMaterials getGTPMaterials();

    Optional<GTPBulletin> findBulletin(Long id);

    Optional<AnnualGTPBulletin> findAnnualReport(Long id);
}
