package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.repository.category.PluviometricPostRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class PluviometricPostServiceImpl extends BaseJpaServiceImpl<PluviometricPost>
        implements PluviometricPostService {

    @Autowired
    private PluviometricPostRepository pluviometricPostRepository;

    @Override
    protected BaseJpaRepository<PluviometricPost, Long> repository() {
        return pluviometricPostRepository;
    }

    @Override
    public PluviometricPost newInstance() {
        return new PluviometricPost();
    }

    @Override
    public JpaRepository<PluviometricPost, Long> getRepository() {
        return pluviometricPostRepository;
    }

    @Override
    public Page<PluviometricPost> searchText(String term, Pageable page) {
        return pluviometricPostRepository.searchText(term, page);
    }
}
