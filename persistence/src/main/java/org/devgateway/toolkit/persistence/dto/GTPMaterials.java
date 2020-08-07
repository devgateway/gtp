package org.devgateway.toolkit.persistence.dto;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;

/**
 * @author Octavian Ciubotaru
 */
public class GTPMaterials {

    private final List<GTPBulletin> bulletins;

    private final List<AnnualGTPReport> annualReports;

    public GTPMaterials(List<GTPBulletin> bulletins,
            List<AnnualGTPReport> annualReports) {
        this.bulletins = bulletins;
        this.annualReports = annualReports;
    }

    public List<GTPBulletin> getBulletins() {
        return bulletins;
    }

    public List<AnnualGTPReport> getAnnualReports() {
        return annualReports;
    }
}
