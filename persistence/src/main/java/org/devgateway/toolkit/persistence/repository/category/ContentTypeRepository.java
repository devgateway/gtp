package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.ContentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Daniel Oliva
 */
@Transactional
public interface ContentTypeRepository extends CategoryRepository<ContentType> {

    @Query("select r "
            + "from ContentType r "
            + "join fetch r.localizedLabels as s ")
    List<ContentType> findAllPopulatedLang();
}
