package org.devgateway.toolkit.persistence.service.reference.rainfall;

import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.dto.rainfall.ReferenceLevels;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.reference.YearsReferenceService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public interface RainLevelReferenceService extends BaseJpaService<RainLevelReference>,
        YearsReferenceService<RainLevelReference> {

    List<ReferenceLevels> findReferenceLevels(Collection<Integer> years, Long pluviometricPostId);

    void export(RainLevelReference rainReference, OutputStream outputStream) throws IOException;

    RainLevelReference getExample(Integer referenceYearStart, Integer referenceYearEnd);
}
