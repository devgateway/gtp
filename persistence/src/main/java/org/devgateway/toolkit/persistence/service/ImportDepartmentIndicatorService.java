package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.DepartmentIndicator;
import org.devgateway.toolkit.persistence.util.ImportResults;

/**
 * Created by Daniel Oliva
 */
@FunctionalInterface
public interface ImportDepartmentIndicatorService {

    ImportResults processFile(DepartmentIndicator dataset);

}