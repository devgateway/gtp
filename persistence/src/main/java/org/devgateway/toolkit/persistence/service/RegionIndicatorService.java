package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.RegionIndicatorDTO;

import java.util.List;


public interface RegionIndicatorService extends BaseJpaService<RegionIndicator> {

    List<RegionIndicatorDTO> findGisIndicatorAndPovertyIndicator(String lang);

    void restoreLeftFlagToFalse();

    void restoreRightFlagToFalse();
}
