package org.devgateway.toolkit.persistence.service.category;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.category.MarketRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.ServiceUtil;
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
    public boolean exists(Department department, MarketType marketType, String name, Long exceptId) {
        return ServiceUtil.exists(marketRepository.findAllNames(department, marketType), name, exceptId);
    }

    @Override
    public boolean exists(Double latitude, Double longitude, Long exceptId) {
        Long safeExceptId = exceptId == null ? -1 : exceptId;
        return marketRepository.existsByLatitudeAndLongitudeAndIdNot(latitude, longitude, safeExceptId);
    }

    @Override
    public List<Market> findByMarketTypeName(String marketTypeName) {
        return marketRepository.findByType_Name(marketTypeName);
    }
}
