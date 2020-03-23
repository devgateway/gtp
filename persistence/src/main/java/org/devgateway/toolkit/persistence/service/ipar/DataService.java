package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Data;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;


public interface DataService extends BaseJpaService<Data> {
    List<Integer> findDistinctYears();
}
