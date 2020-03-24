package org.devgateway.toolkit.web.rest.controller.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Consumption;
import org.devgateway.toolkit.persistence.dto.ipar.ConsumptionDTO;
import org.devgateway.toolkit.persistence.service.ipar.ConsumptionService;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.ConsumptionFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.ConsumptionFilterState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Oliva
 */
 // @RestController
@RequestMapping(value = "/data/consumption")
@CrossOrigin
public class ConsumptionController extends AbstractDatasetController<Consumption, ConsumptionFilterPagingRequest,
        ConsumptionDTO> {

    public ConsumptionController(ConsumptionService datasetService) {
        super(datasetService);
    }

    @Override
    protected Specification<Consumption> getSpecifications(ConsumptionFilterPagingRequest pagingRequest) {
        ConsumptionFilterState filterState = new ConsumptionFilterState(pagingRequest);
        return filterState.getSpecification();
    }

    @Override
    protected ConsumptionDTO getDTO(Consumption indicator, String lang) {
        return new ConsumptionDTO(indicator, lang);
    }
}
