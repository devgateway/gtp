package org.devgateway.toolkit.persistence.service;

import java.util.Optional;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class GTPServiceImpl implements GTPService {

    @Autowired
    private GTPBulletinService bulletinService;

    @Autowired
    private AnnualGTPBulletinService annualGTPBulletinService;

    @Override
    public GTPMaterials getGTPMaterials() {
        return new GTPMaterials(bulletinService.findAllWithUploads(), annualGTPBulletinService.findAllWithUploads());
    }

    @Override
    public Optional<GTPBulletin> findBulletin(Long id) {
        return bulletinService.findById(id);
    }

    @Override
    public Optional<AnnualGTPBulletin> findAnnualReport(Long id) {
        return annualGTPBulletinService.findById(id);
    }
}
