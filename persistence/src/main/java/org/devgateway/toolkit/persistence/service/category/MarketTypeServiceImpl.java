package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.repository.category.MarketTypeRepository;
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
public class MarketTypeServiceImpl extends BaseJpaServiceImpl<MarketType> implements MarketTypeService {

    @Autowired
    private MarketTypeRepository marketTypeRepository;

    @Override
    protected BaseJpaRepository<MarketType, Long> repository() {
        return marketTypeRepository;
    }

    @Override
    public MarketType newInstance() {
        return new MarketType();
    }

    @Override
    public JpaRepository<MarketType, Long> getRepository() {
        return marketTypeRepository;
    }

    @Override
    public Page<MarketType> searchText(String term, Pageable page) {
        return marketTypeRepository.searchText(term, page);
    }
}
