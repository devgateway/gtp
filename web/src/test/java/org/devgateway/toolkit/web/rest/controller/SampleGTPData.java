package org.devgateway.toolkit.web.rest.controller;

import java.time.Month;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FileContent;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.springframework.http.MediaType;

/**
 * @author Octavian Ciubotaru
 */
public class SampleGTPData {

    private final GTPBulletin bulletin1;

    private final AnnualGTPReport annualReport1;

    private final GTPMaterials materials;

    private final GTPMember anacim;

    private final List<GTPMember> members;

    public SampleGTPData() {
        bulletin1 = new GTPBulletin(1L, 2019, Month.OCTOBER, Decadal.FIRST);
        bulletin1.setUploads(ImmutableSet.of(
                new FileMetadata(1L, "GTP Bulletin #126.pdf", MediaType.APPLICATION_PDF_VALUE,
                        new FileContent("<<pdf-data>>".getBytes()))));

        annualReport1 = new AnnualGTPReport(1L, 2018);
        annualReport1.setUploads(ImmutableSet.of(
                new FileMetadata(2L, "Annual Report 2018.pdf", MediaType.APPLICATION_PDF_VALUE,
                        new FileContent("<<pdf-data>>".getBytes()))));

        materials = new GTPMaterials(
                ImmutableList.of(
                        bulletin1,
                        new GTPBulletin(2L, 2019, Month.OCTOBER, Decadal.SECOND),
                        new GTPBulletin(3L, 2019, Month.OCTOBER, Decadal.THIRD),
                        new GTPBulletin(4L, 2020, Month.JUNE, Decadal.FIRST),
                        new GTPBulletin(5L, 2020, Month.JUNE, Decadal.SECOND),
                        new GTPBulletin(6L, 2020, Month.JUNE, Decadal.THIRD)),
                ImmutableList.of(
                        annualReport1,
                        new AnnualGTPReport(2L, 2019),
                        new AnnualGTPReport(3L, 2020)));

        anacim = new GTPMember(1L, "ANACIM", "Description 1", "https://www.anacim.sn/");
        anacim.setLogo(ImmutableSet.of(
                new FileMetadata(3L, "anacim-logo.png", MediaType.IMAGE_PNG_VALUE,
                        new FileContent("<<png-data>>".getBytes()))));

        members = ImmutableList.of(
                anacim,
                new GTPMember(2L, "DAPSA", "Description 2", "http://www.dapsa.gouv.sn/"));
    }

    public GTPBulletin getBulletin1() {
        return bulletin1;
    }

    public AnnualGTPReport getAnnualReport1() {
        return annualReport1;
    }

    public GTPMaterials getMaterials() {
        return materials;
    }

    public GTPMember getAnacim() {
        return anacim;
    }

    public List<GTPMember> getMembers() {
        return members;
    }
}
