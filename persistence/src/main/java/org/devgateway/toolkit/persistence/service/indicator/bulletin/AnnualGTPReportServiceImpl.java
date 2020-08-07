package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import static java.util.stream.Collectors.toMap;

import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.repository.AnnualGTPReportRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class AnnualGTPReportServiceImpl extends BaseJpaServiceImpl<AnnualGTPReport>
        implements AnnualGTPReportService {

    @Autowired
    private AnnualGTPReportRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    public void generate() {
        Map<Integer, AnnualGTPReport> byYear = findAll().stream()
                .collect(toMap(AnnualGTPReport::getYear, Function.identity()));

        Integer startingYear = adminSettingsService.getStartingYear();
        int endYear = Year.now(AD3Clock.systemDefaultZone()).getValue();

        for (int y = startingYear; y <= endYear; y++) {
            if (!byYear.containsKey(y)) {
                repository.save(new AnnualGTPReport(y));
            }
        }
    }

    @Override
    protected BaseJpaRepository<AnnualGTPReport, Long> repository() {
        return repository;
    }

    @Override
    public AnnualGTPReport newInstance() {
        return new AnnualGTPReport();
    }

    @Override
    public List<AnnualGTPReport> findAllWithUploads() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllWithUploads(startingYear);
    }
}
