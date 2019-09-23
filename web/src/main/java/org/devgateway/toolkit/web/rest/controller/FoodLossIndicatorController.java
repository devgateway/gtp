package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/foodLoss")
@CrossOrigin
public class FoodLossIndicatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodLossIndicatorController.class);

    @Autowired
    private FoodLossIndicatorService service;

    @CrossOrigin
    @ApiOperation(value = "Get paginated food loss data")
    @RequestMapping(value = "/all", method = GET)
    public Page<FoodLossIndicator> getAll(@ModelAttribute @Valid final FoodLossFilterPagingRequest pageRequest) {
        LOGGER.debug("get food loss data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        FoodLossFilterState filter = new FoodLossFilterState(pageRequest);
        return service.findAll(filter.getSpecification(), pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Dump agricutural women dataset")
    @RequestMapping(value = "/dump", method = GET)
    public List<FoodLossIndicator> getAll() {
        return service.findAll();
    }

}
