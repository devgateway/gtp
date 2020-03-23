package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.ipar.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RegionIndicatorService extends BaseJpaService<RegionIndicator> {

    List<GisIndicatorDTO> findGisRegionIndicators(String lang);

}
