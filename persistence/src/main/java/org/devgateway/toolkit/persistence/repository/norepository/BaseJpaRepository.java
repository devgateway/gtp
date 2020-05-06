package org.devgateway.toolkit.persistence.repository.norepository;

import org.devgateway.toolkit.persistence.repository.CacheHibernateQueryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by Octavian on 01.07.2016.
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @Override
    @CacheHibernateQueryResult
    Page<T> findAll(Specification<T> spec, Pageable pageable);
}
