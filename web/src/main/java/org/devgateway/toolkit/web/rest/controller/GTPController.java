package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsConfig;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsData;
import org.devgateway.toolkit.persistence.dto.GTPMaterialsFilter;
import org.devgateway.toolkit.persistence.service.GTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@RestController
@RequestMapping("/api/gtp")
public class GTPController {

    private static final String HEADER_IF_NONE_MATCH = "If-None-Match";

    @Autowired
    private GTPService service;

    @GetMapping("materials/all")
    public GTPMaterialsData getGTPMaterials() {
        return service.getGTPMaterials();
    }

    @GetMapping("materials/config")
    public GTPMaterialsConfig getGTPMaterialsConfig() {
        return service.getGTPMaterialsConfig();
    }

    @PostMapping("materials/data")
    public GTPMaterials getGTPMaterialsFiltered(@RequestBody @Valid GTPMaterialsFilter filter) {
        return service.getGTPMaterialsFiltered(filter);
    }

    @GetMapping("bulletin")
    public ResponseEntity<Resource> getBulletin(@RequestParam("id") Long id) {
        return service.findBulletin(id).map(GTPBulletin::getUpload)
                .map(GTPController::responseForFileMetadata)
                .orElseGet(GTPController::notFound);
    }

    @GetMapping("annual-report")
    public ResponseEntity<Resource> getAnnualReport(@RequestParam("id") Long id) {
        return service.findAnnualReport(id).map(AnnualGTPReport::getUpload)
                .map(GTPController::responseForFileMetadata)
                .orElseGet(GTPController::notFound);
    }

    @GetMapping("members")
    public List<GTPMember> getMembers() {
        return service.getGTPMembers();
    }

    @GetMapping("member/logo")
    public ResponseEntity<Resource> getMemberLogo(@RequestParam("id") Long id,
            @RequestHeader(value = HEADER_IF_NONE_MATCH, required = false) String ifNoneMatch) {
        return service.getMember(id).map(GTPMember::getLogoSingle)
                .map(m -> responseForFileMetadata(m, ifNoneMatch))
                .orElseGet(GTPController::notFound);
    }

    private static ResponseEntity<Resource> responseForFileMetadata(FileMetadata metadata) {
        return responseForFileMetadata(metadata, null);
    }

    private static ResponseEntity<Resource> responseForFileMetadata(FileMetadata metadata, String ifNoneMatch) {
        String contentDispositionValue = ContentDisposition.builder("attachment")
                .filename(metadata.getName())
                .build()
                .toString();

        String etag = "\"" + metadata.getId().toString() + "\"";

        if (ifNoneMatch != null && (ifNoneMatch.equals("*") || ifNoneMatch.equals(etag))) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(etag)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .eTag(etag)
                    .cacheControl(CacheControl.noCache().mustRevalidate())
                    .contentType(MediaType.parseMediaType(metadata.getContentType()))
                    .contentLength(metadata.getSize())
                    .header("Content-Disposition", contentDispositionValue)
                    .body(new ByteArrayResource(metadata.getContent().getBytes()));
        }
    }

    private static ResponseEntity<Resource> notFound() {
        return ResponseEntity.notFound().build();
    }

}
