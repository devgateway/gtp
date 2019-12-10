package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.devgateway.toolkit.web.rest.controller.filter.DatasetFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.DatasetFilterState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/data")
@CrossOrigin
public class DatasetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasetController.class);

    @Autowired
    @Qualifier(value = "datasetService")
    private DatasetService datasetService;

    @CrossOrigin
    @ApiOperation(value = "Get all datasets metadata")
    @RequestMapping(value = "/dataset/all", method = POST)
    public @ResponseBody
    Page<DatasetDTO> getAllIndicatorMetadata(@RequestBody @Valid final DatasetFilterPagingRequest request) {
        LOGGER.info("get all datasets");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.Direction.DESC, "id");
        DatasetFilterState filter = new DatasetFilterState(request);
        return datasetService.findAllDTO(filter.getSpecification(), pageable);
    }
}
