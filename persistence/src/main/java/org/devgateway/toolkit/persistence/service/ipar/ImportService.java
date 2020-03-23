package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.util.ImportResults;

/**
 * Created by Daniel Oliva
 */
@FunctionalInterface
public interface ImportService {

    ImportResults processFile(Dataset dataset);

}