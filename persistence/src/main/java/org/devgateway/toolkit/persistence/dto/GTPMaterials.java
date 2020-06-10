package org.devgateway.toolkit.persistence.dto;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;

/**
 * @author Octavian Ciubotaru
 */
public class GTPMaterials {

    private final List<GTPBulletin> bulletins;

    private final List<AnnualGTPBulletin> annualReports;

    public GTPMaterials(List<GTPBulletin> bulletins,
            List<AnnualGTPBulletin> annualReports) {
        this.bulletins = bulletins;
        this.annualReports = annualReports;
    }

    public List<GTPBulletin> getBulletins() {
        return bulletins;
    }

    public List<AnnualGTPBulletin> getAnnualReports() {
        return annualReports;
    }
}
