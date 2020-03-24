package org.devgateway.toolkit.persistence.repository.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.ContentType;
import org.devgateway.toolkit.persistence.repository.category.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface ContentTypeRepository extends CategoryRepository<ContentType> {

    @Query("select r "
            + "from ContentType r "
            + "join fetch r.localizedLabels as s ")
    List<ContentType> findAllPopulatedLang();
}
