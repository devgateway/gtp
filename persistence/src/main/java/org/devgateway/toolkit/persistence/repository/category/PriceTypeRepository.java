package org.devgateway.toolkit.persistence.repository.category;

import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface PriceTypeRepository extends CategoryRepository<PriceType>,
        TextSearchableRepository<PriceType, Long> {
}
