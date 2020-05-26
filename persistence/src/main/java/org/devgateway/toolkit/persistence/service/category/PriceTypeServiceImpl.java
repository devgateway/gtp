package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.repository.category.PriceTypeRepository;
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
public class PriceTypeServiceImpl extends BaseJpaServiceImpl<PriceType> implements PriceTypeService {

    @Autowired
    private PriceTypeRepository priceTypeRepository;

    @Override
    protected BaseJpaRepository<PriceType, Long> repository() {
        return priceTypeRepository;
    }

    @Override
    public PriceType newInstance() {
        return new PriceType();
    }

    @Override
    public JpaRepository<PriceType, Long> getRepository() {
        return priceTypeRepository;
    }

    @Override
    public Page<PriceType> searchText(String term, Pageable page) {
        return priceTypeRepository.searchText(term, page);
    }
}
