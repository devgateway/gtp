package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.ipar.Market;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.category.MarketRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class MarketServiceImpl extends BaseJpaServiceImpl<Market> implements MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    protected BaseJpaRepository<Market, Long> repository() {
        return marketRepository;
    }

    @Override
    public Market newInstance() {
        return new Market();
    }

    @Override
    public boolean exists(Department department, String name, Long exceptId) {
        Long safeExceptId = exceptId == null ? -1 : exceptId;
        return marketRepository.existsByDepartmentAndNameAndIdNot(department, name, safeExceptId);
    }

    @Override
    public boolean exists(Double latitude, Double longitude, Long exceptId) {
        Long safeExceptId = exceptId == null ? -1 : exceptId;
        return marketRepository.existsByLatitudeAndLongitudeAndIdNot(latitude, longitude, safeExceptId);
    }
}
