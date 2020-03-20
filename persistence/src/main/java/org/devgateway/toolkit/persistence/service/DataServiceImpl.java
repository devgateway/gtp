package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.repository.DataRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class DataServiceImpl extends BaseJpaServiceImpl<Data> implements DataService {

    @Autowired
    private DataRepository repository;

    @Override
    protected BaseJpaRepository<Data, Long> repository() {
        return repository;
    }

    @Override
    public Data newInstance() {
        return null;
    }


    @Override
    public List<Integer> findDistinctYears() {
        return repository.findDistinctYears();
    }
}
