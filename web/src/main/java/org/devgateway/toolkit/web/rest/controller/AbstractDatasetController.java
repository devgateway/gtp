package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.service.AbstractDatasetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Octavian Ciubotaru
 */
public class AbstractDatasetController<T extends AbstractAuditableEntity & Serializable> {

    private AbstractDatasetService<T> datasetService;

    AbstractDatasetController(AbstractDatasetService<T> datasetService) {
        this.datasetService = datasetService;
    }

    @CrossOrigin
    @ApiOperation(value = "Dump data")
    @RequestMapping(value = "/dump", method = GET)
    public ResponseEntity<List<T>> getAll(WebRequest webRequest) {
        String eTag = datasetService.getETagForDump();

        if (eTag != null && webRequest.checkNotModified(eTag)) {
            return null;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        if (eTag != null) {
            responseBuilder.eTag(eTag);
        }

        return responseBuilder.body(datasetService.findAll());
    }
}
