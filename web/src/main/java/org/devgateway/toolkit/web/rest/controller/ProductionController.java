package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.service.ProductionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/production")
@CrossOrigin
public class ProductionController extends AbstractDatasetController<Production> {

    public ProductionController(ProductionService productionService) {
        super(productionService);
    }
}
