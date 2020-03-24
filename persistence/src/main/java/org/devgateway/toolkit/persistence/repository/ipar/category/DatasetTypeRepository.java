package org.devgateway.toolkit.persistence.repository.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.DatasetType;
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
public interface DatasetTypeRepository extends CategoryRepository<DatasetType> {

    @Query("select r "
            + "from DatasetType r "
            + "join fetch r.localizedLabels as s ")
    List<DatasetType> findAllPopulatedLang();
}
