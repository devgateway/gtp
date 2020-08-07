package org.devgateway.toolkit.persistence.service;

import java.util.List;
import java.util.Optional;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.AnnualGTPReportService;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.GTPBulletinService;
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
    private GTPMemberService memberService;

    @Autowired
    private AnnualGTPReportService annualGTPBulletinService;

    @Override
    public GTPMaterials getGTPMaterials() {
        return new GTPMaterials(bulletinService.findAllWithUploads(), annualGTPBulletinService.findAllWithUploads());
    }

    @Override
    public List<GTPMember> getGTPMembers() {
        return memberService.findAll();
    }

    @Override
    public Optional<GTPMember> getMember(Long id) {
        return memberService.findById(id);
    }

    @Override
    public Optional<GTPBulletin> findBulletin(Long id) {
        return bulletinService.findById(id);
    }

    @Override
    public Optional<AnnualGTPReport> findAnnualReport(Long id) {
        return annualGTPBulletinService.findById(id);
    }
}
