package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.WebContent;
import org.devgateway.toolkit.persistence.service.WebContentService;
import org.devgateway.toolkit.web.rest.controller.filter.GenericPagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @RequestMapping(value = "/all", method = POST)
    public @ResponseBody
    Page<WebContent> getAll(@RequestBody @Valid final GenericPagingRequest request) {
        LOGGER.info("get all content");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        return service.findAll(pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Get web content by id.")
    @RequestMapping(value = "/{id}", method = {GET, POST})
    public @ResponseBody Optional<WebContent> getContentById(@PathVariable final long id) {
        LOGGER.info("get content by id: " + id);
        return service.findById(id);
    }
}
