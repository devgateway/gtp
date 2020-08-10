package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.repository.category.LivestockDiseaseRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class LivestockDiseaseServiceImpl extends BaseJpaServiceImpl<LivestockDisease>
        implements LivestockDiseaseService {

    @Autowired
    private LivestockDiseaseRepository repository;

    @Override
    protected BaseJpaRepository<LivestockDisease, Long> repository() {
        return repository;
    }

    @Override
    public LivestockDisease newInstance() {
        return new LivestockDisease();
    }

    @Override
    public UniquePropertyRepository<LivestockDisease, Long> uniquePropertyRepository() {
        return repository;
    }
}
