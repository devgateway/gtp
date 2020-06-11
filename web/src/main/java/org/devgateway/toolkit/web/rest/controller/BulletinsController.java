package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.dto.GTPMaterials;
import org.devgateway.toolkit.persistence.service.GTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Octavian Ciubotaru
 */
@RestController
@RequestMapping("/api/gtp")
public class BulletinsController {

    @Autowired
    private GTPService service;

    @GetMapping
    public GTPMaterials getGTPMaterials() {
        return service.getGTPMaterials();
    }

    @GetMapping("bulletin")
    public ResponseEntity<Resource> getBulletin(@RequestParam("id") Long id) {
        return service.findBulletin(id).map(GTPBulletin::getUpload)
                .map(BulletinsController::responseForFileMetadata)
                .orElseGet(BulletinsController::notFound);
    }

    @GetMapping("annual-report")
    public ResponseEntity<Resource> getAnnualReport(@RequestParam("id") Long id) {
        return service.findAnnualReport(id).map(AnnualGTPReport::getUpload)
                .map(BulletinsController::responseForFileMetadata)
                .orElseGet(BulletinsController::notFound);
    }

    private static ResponseEntity<Resource> responseForFileMetadata(FileMetadata metadata) {
        String contentDispositionValue = ContentDisposition.builder("attachment")
                .filename(metadata.getName())
                .build()
                .toString();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .contentLength(metadata.getSize())
                .header("Content-Disposition", contentDispositionValue)
                .body(new ByteArrayResource(metadata.getContent().getBytes()));
    }

    private static ResponseEntity<Resource> notFound() {
        return ResponseEntity.notFound().build();
    }

}
