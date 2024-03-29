package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsConfig;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsData;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsFilter;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.AnnualGTPReportService;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.GTPBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public GTPMaterialsData getGTPMaterials() {
        GTPMaterialsFilter filter = new GTPMaterialsFilter(null);
        return new GTPMaterialsData(getGTPMaterialsConfig(), filter, getGTPMaterialsFiltered(filter));
    }

    @Override
    public GTPMaterialsConfig getGTPMaterialsConfig() {
        Set<Department> deps = bulletinService.findDepartments();
        deps.addAll(annualGTPBulletinService.findDepartments());
        deps.add(new Department("National"));
        return new GTPMaterialsConfig(new ArrayList<>(deps));
    }

    @Override
    public GTPMaterials getGTPMaterialsFiltered(GTPMaterialsFilter filter) {
        return new GTPMaterials(
                bulletinService.findAllWithUploadsAndLocation(filter.getLocationId()),
                annualGTPBulletinService.findAllWithUploadsAndDepartment(filter.getLocationId()));
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
