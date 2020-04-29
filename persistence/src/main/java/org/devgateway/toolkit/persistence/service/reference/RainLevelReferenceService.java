package org.devgateway.toolkit.persistence.service.reference;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dto.rainfall.ReferenceLevels;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

/**
 * @author Nadejda Mandrescu
 */
public interface RainLevelReferenceService extends BaseJpaService<RainLevelReference>,
        YearsReferenceService<RainLevelReference> {

    List<ReferenceLevels> findReferenceLevels(Collection<Integer> years, Long pluviometricPostId);
}
