package org.devgateway.toolkit.web.rest.controller.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.devgateway.toolkit.persistence.dto.ipar.ProductionDTO;
import org.devgateway.toolkit.persistence.service.ipar.ProductionService;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.ProductionFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.ProductionFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
 // @RestController
@RequestMapping(value = "/data/production")
@CrossOrigin
public class ProductionController extends AbstractDatasetController<Production, ProductionFilterPagingRequest,
        ProductionDTO> {

    public ProductionController(ProductionService productionService) {
        super(productionService);
    }

    @Override
    protected Specification<Production> getSpecifications(ProductionFilterPagingRequest request) {
        ProductionFilterState filterState = new ProductionFilterState(request);
        return filterState.getSpecification();
    }

    @Override
    protected ProductionDTO getDTO(Production indicator, String lang) {
        return new ProductionDTO(indicator, lang);
    }
}