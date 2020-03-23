package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.Data;

import java.util.List;


public interface DataService extends BaseJpaService<Data>  {
    List<Integer> findDistinctYears();
}
