package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.devgateway.toolkit.persistence.service.UniquePropertyService;

/**
 * @author Nadejda Mandrescu
 */
public interface PluviometricPostService extends TextSearchableService<PluviometricPost>,
        BaseJpaService<PluviometricPost>, UniquePropertyService<PluviometricPost> {
}
