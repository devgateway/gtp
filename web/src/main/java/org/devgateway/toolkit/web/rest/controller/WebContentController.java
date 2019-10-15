package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.WebContent;
import org.devgateway.toolkit.persistence.service.WebContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/webContent")
@CrossOrigin
public class WebContentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebContentController.class);

    @Autowired
    private WebContentService service;

    @CrossOrigin
    @ApiOperation(value = "Get web content by id.")
    @RequestMapping(value = "/{id}", method = GET)
    public Optional<WebContent> getContentById(@PathVariable final long id) {
        LOGGER.info("get content by id: " + id);
        return service.findById(id);
    }
}
