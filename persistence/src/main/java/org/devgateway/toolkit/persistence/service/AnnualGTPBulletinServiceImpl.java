package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.toMap;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.repository.AnnualGTPBulletinRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class AnnualGTPBulletinServiceImpl extends BaseJpaServiceImpl<AnnualGTPBulletin>
        implements AnnualGTPBulletinService  {

    @Autowired
    private AnnualGTPBulletinRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    public void generate() {
        Map<Integer, AnnualGTPBulletin> byYear = findAll().stream()
                .collect(toMap(AnnualGTPBulletin::getYear, Function.identity()));

        Integer startingYear = adminSettingsService.getStartingYear();
        int endYear = Year.now().getValue();

        for (int y = startingYear; y <= endYear; y++) {
            if (!byYear.containsKey(y)) {
                repository.save(new AnnualGTPBulletin(y));
            }
        }
    }

    @Override
    public List<AnnualGTPBulletin> findAllWithUploads() {
        return repository.findAllWithUploads();
    }

    @Override
    protected BaseJpaRepository<AnnualGTPBulletin, Long> repository() {
        return repository;
    }

    @Override
    public AnnualGTPBulletin newInstance() {
        return new AnnualGTPBulletin();
    }
}
