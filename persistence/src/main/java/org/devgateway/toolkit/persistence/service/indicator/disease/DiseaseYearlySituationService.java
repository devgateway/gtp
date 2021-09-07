package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.status.DiseasesProgress;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public interface DiseaseYearlySituationService extends BaseJpaService<DiseaseYearlySituation> {

    void generate();

    boolean existsByYear(Integer year);

    DiseaseYearlySituation getExample(Integer year);

    void export(DiseaseYearlySituation diseaseYearlySituation, OutputStream outputStream) throws IOException;

    List<Integer> findYearsWithQuantities();

    Month findLastMonthWithQuantities(Integer year);

    Long getDiseaseIdWithMaximumQuantity(YearMonth yearMonth);

    List<DiseaseQuantity> findQuantities(Integer year, Long diseaseId);

    DiseasesProgress getProgress(Integer year);
}
