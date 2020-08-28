package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsConfig;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsData;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsFilter;

import java.util.List;
import java.util.Optional;

/**
 * @author Octavian Ciubotaru
 */
public interface GTPService {

    GTPMaterialsData getGTPMaterials();

    GTPMaterialsConfig getGTPMaterialsConfig();

    GTPMaterials getGTPMaterialsFiltered(GTPMaterialsFilter filter);

    List<GTPMember> getGTPMembers();

    Optional<GTPMember> getMember(Long id);

    Optional<GTPBulletin> findBulletin(Long id);

    Optional<AnnualGTPReport> findAnnualReport(Long id);
}
