package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface PluviometricPostRepository extends TextSearchableRepository<PluviometricPost, Long>,
        UniquePropertyRepository<PluviometricPost, Long> {

    @Override
    @Query("select o from  #{#entityName} o where lower(o.label) like %:label%")
    Page<PluviometricPost> searchText(@Param("label") String label, Pageable page);

    List<PluviometricPost> findAllByIdNotIn(List<Long> ids);
}
