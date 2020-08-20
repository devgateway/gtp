package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.repository.indicator.DiseaseYearlySituationRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.OutputStream;
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
    public DiseaseYearlySituation newInstance() {
        return new DiseaseYearlySituation();
    }
}
