package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.RegionIndicator;
import org.devgateway.toolkit.persistence.util.ImportResults;

/**
 * Created by Daniel Oliva
 */
@FunctionalInterface
public interface ImportRegionIndicatorService {

    ImportResults processFile(RegionIndicator dataset);

}