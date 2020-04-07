package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RegionIndicatorService extends BaseJpaService<RegionIndicator> {

    List<GisIndicatorDTO> findGisRegionIndicators(String lang);

    List<GisIndicatorDTO> getFakeIndicatorDTOs(String lang);

    List<GisIndicatorDTO> getFakeIndicatorDTOsMinimal(String lang);

    List<RegionIndicator> findAllFake();

    void deleteAllFake();
}
