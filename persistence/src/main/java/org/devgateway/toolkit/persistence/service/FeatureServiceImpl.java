package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Feature;
import org.devgateway.toolkit.persistence.repository.FeatureRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeatureServiceImpl extends BaseJpaServiceImpl<Feature> implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    protected BaseJpaRepository<Feature, Long> repository() {
        return featureRepository;
    }

    @Override
    public Feature newInstance() {
        return null;
    }
}
