package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.repository.RiverStationRepository;
import org.devgateway.toolkit.persistence.repository.indicator.RiverStationYearlyLevelsRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class RiverStationYearlyLevelsServiceImpl extends BaseJpaServiceImpl<RiverStationYearlyLevels>
        implements RiverStationYearlyLevelsService {

    @Autowired
    private RiverStationYearlyLevelsRepository repository;

    @Autowired
    private RiverStationRepository riverStationRepository;

    @Override
    protected BaseJpaRepository<RiverStationYearlyLevels, Long> repository() {
        return repository;
    }

    @Override
    public boolean existsByYear(HydrologicalYear year) {
        return false;
    }

    @Override
    public void generate(HydrologicalYear year) {
        List<RiverStation> riverStations = riverStationRepository.findAll();

        List<RiverStationYearlyLevels> l = repository.findByYear(year);

        Set<RiverStation> existingStations = l.stream()
                .map(RiverStationYearlyLevels::getStation)
                .collect(toCollection(HashSet::new));

        riverStations.stream()
                .filter(rs -> !existingStations.contains(rs))
                .map(rs -> new RiverStationYearlyLevels(rs, year))
                .forEach(repository::save);
    }

    @Override
    public RiverStationYearlyLevels newInstance() {
        return new RiverStationYearlyLevels();
    }

    @Override
    public List<HydrologicalYear> findYearsWithLevels() {
        return repository.findYearsWithLevels();
    }

    @Override
    public List<RiverStation> findStationsWithLevels() {
        return repository.findStationsWithLevels();
    }

    @Override
    public List<RiverStationYearlyLevels> findByYearInAndStationId(Set<HydrologicalYear> years, Long riverStationId) {
        return repository.findByYearInAndStationId(years, riverStationId);
    }
}
