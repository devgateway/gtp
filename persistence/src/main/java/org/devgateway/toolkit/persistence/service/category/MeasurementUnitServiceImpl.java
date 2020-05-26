package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.repository.category.MeasurementUnitRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class MeasurementUnitServiceImpl extends BaseJpaServiceImpl<MeasurementUnit> implements MeasurementUnitService {

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Override
    protected BaseJpaRepository<MeasurementUnit, Long> repository() {
        return measurementUnitRepository;
    }

    @Override
    public MeasurementUnit newInstance() {
        return new MeasurementUnit();
    }

    @Override
    public JpaRepository<MeasurementUnit, Long> getRepository() {
        return measurementUnitRepository;
    }

    @Override
    public Page<MeasurementUnit> searchText(String term, Pageable page) {
        return measurementUnitRepository.searchText(term, page);
    }
}
