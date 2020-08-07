package org.devgateway.toolkit.persistence.service;

import java.util.List;
import java.util.Optional;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPService {

    GTPMaterials getGTPMaterials();

    List<GTPMember> getGTPMembers();

    Optional<GTPMember> getMember(Long id);

    Optional<GTPBulletin> findBulletin(Long id);

    Optional<AnnualGTPReport> findAnnualReport(Long id);
}
