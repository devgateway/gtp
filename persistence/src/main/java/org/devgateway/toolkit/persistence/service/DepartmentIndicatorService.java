package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DepartmentIndicatorService extends BaseJpaService<DepartmentIndicator> {

    List<GisIndicatorDTO> findGisDepartmentIndicators(String lang);

    List<GisIndicatorDTO> getFakeIndicatorDTOs(String lang);

    List<DepartmentIndicator> findAllFake();
}
