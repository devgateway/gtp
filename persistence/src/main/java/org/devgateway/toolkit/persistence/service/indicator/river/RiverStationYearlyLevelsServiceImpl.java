package org.devgateway.toolkit.persistence.service.indicator.river;

import static java.util.stream.Collectors.toCollection;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.repository.RiverStationRepository;
import org.devgateway.toolkit.persistence.repository.indicator.RiverStationYearlyLevelsRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.RiverStationStatus;
import org.devgateway.toolkit.persistence.status.RiverStationsYearProgress;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private AdminSettingsService adminSettingsService;

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
        HydrologicalYear minYear = new HydrologicalYear(adminSettingsService.getStartingYear());
        return repository.findYearsWithLevels(minYear);
    }

    @Override
    public List<RiverStation> findStationsWithLevels() {
        return repository.findStationsWithLevels();
    }

    @Override
    public List<RiverStationYearlyLevels> findByYearInAndStationId(Set<HydrologicalYear> years, Long riverStationId) {
        return repository.findByYearInAndStationId(years, riverStationId);
    }

    public boolean hasLevels(Set<HydrologicalYear> years, Long riverStationId) {
        return repository.countLevels(years, riverStationId) > 0;
    }

    @Override
    public List<RiverStation> findStationsWithLevels(Set<HydrologicalYear> years) {
        return repository.findStationsWithLevels(years);
    }

    @Override
    public RiverStationsYearProgress getProgress(Integer year) {
        List<RiverStation> riverStations = riverStationRepository.findAll();

        List<RiverStationYearlyLevels> l = repository.findByYear(new HydrologicalYear(year));

        List<RiverStationStatus> riverStationStatuses = new ArrayList<>();

        YearMonth now = YearMonth.now(AD3Clock.systemDefaultZone());

        for (RiverStation riverStation : riverStations) {
            Set<Month> monthsWithData = l.stream().filter(z -> z.getStation().equals(riverStation))
                    .flatMap(z -> z.getLevels().stream().map(rl -> rl.getMonthDay().getMonth()))
                    .collect(Collectors.toSet());

            Map<Month, DataEntryStatus> statuses = new HashMap<>();

            for (Month month : HydrologicalYear.HYDROLOGICAL_MONTHS) {
                statuses.put(month, monthsWithData.contains(month)
                        ? DataEntryStatus.PUBLISHED
                        : (hydrologicalYearMonth(year, month).compareTo(now) < 0
                                ? DataEntryStatus.NO_DATA
                                : DataEntryStatus.NOT_APPLICABLE));
            }

            riverStationStatuses.add(new RiverStationStatus(riverStation, statuses));
        }

        return new RiverStationsYearProgress(riverStationStatuses);
    }

    private YearMonth hydrologicalYearMonth(int year, Month month) {
        int offset = month.compareTo(HydrologicalYear.HYDROLOGICAL_FIRST_MONTH) < 0 ? 1 : 0;
        return YearMonth.of(year + offset, month);
    }
}
