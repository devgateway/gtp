package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.service.menu.CNSCHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
