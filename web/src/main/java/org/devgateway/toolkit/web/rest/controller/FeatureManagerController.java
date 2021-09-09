package org.devgateway.toolkit.web.rest.controller;

import static java.util.stream.Collectors.toList;

import org.devgateway.toolkit.persistence.dao.Feature;
import org.devgateway.toolkit.persistence.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@RestController
public class FeatureManagerController {

    @Autowired
    private FeatureService featureService;

    @GetMapping("/api/enabled-features")
    public List<String> getFeatures() {
        return featureService.findAll().stream()
                .filter(Feature::isEnabledInHierarchy)
                .map(Feature::getName)
                .collect(toList());
    }
}
