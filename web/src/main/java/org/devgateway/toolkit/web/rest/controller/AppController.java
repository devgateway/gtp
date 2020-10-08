package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.service.menu.CNSCHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nadejda Mandrescu
 */
@RestController
@RequestMapping("/api/app")
public class AppController {

    @Autowired
    private CNSCHeaderService cnscHeaderService;

    @GetMapping("cnsc-header")
    public CNSCHeader getCNSCHeader() {
        return cnscHeaderService.get();
    }

    @GetMapping("cnsc-header/logo")
    public ResponseEntity<Resource> getCNSCHeader(
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        CNSCHeader header = cnscHeaderService.get();
        return header == null ? ResponseEntity.notFound().build()
                : FileMetadataController.responseForFileMetadata(header.getLogoSingle(), ifNoneMatch);
    }
}
