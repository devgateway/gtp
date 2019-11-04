package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.service.AbstractDatasetService;
import org.devgateway.toolkit.web.rest.controller.filter.DefaultFilterPagingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * @author Octavian Ciubotaru
 */

public abstract class AbstractDatasetController<T extends AbstractAuditableEntity & Serializable,
        S extends DefaultFilterPagingRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatasetController.class);
    public static final String MIN = "min";
    public static final String MAX = "max";

    protected AbstractDatasetService<T> datasetService;

    AbstractDatasetController(AbstractDatasetService<T> datasetService) {
        this.datasetService = datasetService;
    }

    protected abstract Specification<T> getSpecifications(S request);

    @CrossOrigin
    @ApiOperation(value = "Get validated data paginated")
    @RequestMapping(value = "/all", method = POST)
    public @ResponseBody Page<T> getPaginated(@RequestBody @Valid final S pageRequest) {
        LOGGER.debug("get all validated data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        return datasetService.findAll(getSpecifications(pageRequest), pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Dump validated data")
    @RequestMapping(value = "/dump", method = POST)
    public @ResponseBody ResponseEntity<List<T>> getAllValidated(WebRequest webRequest,
                                          @RequestBody @Valid final S pageRequest) {
        ResponseEntity.BodyBuilder responseBuilder = getBodyBuilder(webRequest);
        if (responseBuilder == null) {
            return null;
        }

        return responseBuilder.body(datasetService.findAll(getSpecifications(pageRequest)));
    }

    @CrossOrigin
    @ApiOperation(value = "Dump all data, even not validated")
    @RequestMapping(value = "/unchecked", method = POST)
    public @ResponseBody ResponseEntity<List<T>> getAllData(WebRequest webRequest) {
        ResponseEntity.BodyBuilder responseBuilder = getBodyBuilder(webRequest);
        if (responseBuilder == null) {
            return null;
        }
        return responseBuilder.body(datasetService.findAll());
    }



    private ResponseEntity.BodyBuilder getBodyBuilder(WebRequest webRequest) {
        String eTag = datasetService.getETagForDump();

        if (eTag != null && webRequest.checkNotModified(eTag)) {
            return null;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        if (eTag != null) {
            responseBuilder.eTag(eTag);
        }
        return responseBuilder;
    }
}
