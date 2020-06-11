package org.devgateway.toolkit.persistence.service;

import java.util.Optional;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPService {

    GTPMaterials getGTPMaterials();

    Optional<GTPBulletin> findBulletin(Long id);

    Optional<AnnualGTPReport> findAnnualReport(Long id);
}
