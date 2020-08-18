package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public interface DiseaseYearlySituationService extends BaseJpaService<DiseaseYearlySituation> {

    void generate();

    boolean existsByYear(Integer year);

    DiseaseYearlySituation getExample(Integer year);

    void export(DiseaseYearlySituation diseaseYearlySituation, OutputStream outputStream) throws IOException;
}
