package org.devgateway.toolkit.web.rest.controller;

import java.time.Month;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FileContent;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.springframework.http.MediaType;

/**
 * 20%
 *
 * 1164.84
 *
 * @author Octavian Ciubotaru
 */
public class SampleGTPBulletins {

    private final GTPBulletin bulletin1;

    private final AnnualGTPBulletin annualReport1;

    private final GTPMaterials materials;

    public SampleGTPBulletins() {
        bulletin1 = new GTPBulletin(1L, 2019, Month.OCTOBER, Decadal.FIRST);
        bulletin1.setUploads(ImmutableSet.of(
                new FileMetadata("GTP Bulletin #126.pdf", MediaType.APPLICATION_PDF_VALUE,
                        new FileContent("<<pdf-data>>".getBytes()))));

        annualReport1 = new AnnualGTPBulletin(1L, 2018);
        annualReport1.setUploads(ImmutableSet.of(
                new FileMetadata("Annual Report 2018.pdf", MediaType.APPLICATION_PDF_VALUE,
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
                        new AnnualGTPBulletin(2L, 2019),
                        new AnnualGTPBulletin(3L, 2020)));
    }

    public GTPBulletin getBulletin1() {
        return bulletin1;
    }

    public AnnualGTPBulletin getAnnualReport1() {
        return annualReport1;
    }

    public GTPMaterials getMaterials() {
        return materials;
    }
}
