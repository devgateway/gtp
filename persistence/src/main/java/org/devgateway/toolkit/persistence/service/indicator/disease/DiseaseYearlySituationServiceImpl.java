package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.repository.indicator.DiseaseYearlySituationRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class DiseaseYearlySituationServiceImpl extends BaseJpaServiceImpl<DiseaseYearlySituation>
    implements DiseaseYearlySituationService {

    @Autowired
    private DiseaseYearlySituationRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private EntityManager entityManager;

    @Override
    protected BaseJpaRepository<DiseaseYearlySituation, Long> repository() {
        return repository;
    }

    @Override
    @Transactional
    public DiseaseYearlySituation saveAndFlush(final DiseaseYearlySituation entity) {
        DiseaseYearlySituation saved = super.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    @Override
    @Transactional
    public void generate() {
        Map<Integer, DiseaseYearlySituation> diseaseByY = findAll().stream().collect(
                Collectors.toMap(DiseaseYearlySituation::getYear, dy -> dy));

        for (Integer year : adminSettingsService.getYears()) {
            if (!diseaseByY.containsKey(year)) {
                saveAndFlush(new DiseaseYearlySituation(year));
            }
        }
    }

    @Override
    public boolean existsByYear(Integer year) {
        return repository.existsByYear(year);
    }

    @Override
    public DiseaseYearlySituation getExample(Integer year) {
        return new DiseaseYearlySituation(year);
    }

    @Override
    public void export(DiseaseYearlySituation diseaseYearlySituation, OutputStream outputStream) throws IOException {
        DiseaseQuantityWriter writer = new DiseaseQuantityWriter(diseaseYearlySituation);
        writer.write(outputStream);
    }

    @Override
    public List<Integer> findYearsWithQuantities() {
        return repository.findYearsWithQuantities();
    }

    @Override
    public Month findLastMonthWithQuantities(Integer year) {
        return repository.findLastMonthWithQuantities(year);
    }

    @Override
    public Long getDiseaseIdWithMaximumQuantity(YearMonth ym) {
        Page<Object[]> ds = repository.getDiseaseWithMaximumQuantity(ym.getYear(), ym.getMonth(), PageRequest.of(0, 1));
        return (ds == null || ds.isEmpty()) ? null : (Long) ds.iterator().next()[0];
    }

    @Override
    public List<DiseaseQuantity> findQuantities(Integer year, Long diseaseId) {
        return repository.findQuantities(year, diseaseId);
    }

    @Override
    public DiseaseYearlySituation newInstance() {
        return new DiseaseYearlySituation();
    }
}
