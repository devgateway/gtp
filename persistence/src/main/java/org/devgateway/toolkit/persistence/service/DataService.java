package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Data;

import java.util.List;


public interface DataService extends BaseJpaService<Data>  {
    List<Integer> findDistinctYears();
}
