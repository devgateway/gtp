package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.AnnualGTPReportRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void generate() {
        Set<AnnualGTPReport> byYL = new TreeSet<>(Comparator.comparing(AnnualGTPReport::getYear)
                .thenComparing((a, b) -> Department.compareTo(a.getDepartment(), b.getDepartment())));
        byYL.addAll(findAll());

        Integer startingYear = adminSettingsService.getStartingYear();
        int endYear = Year.now(AD3Clock.systemDefaultZone()).getValue();

        List<Department> ds = departmentService.findAll();
        // National
        ds.add(null);

        for (int y = startingYear; y <= endYear; y++) {
            for (Department d : ds) {
                AnnualGTPReport r = new AnnualGTPReport(y, d);
                if (!byYL.contains(r)) {
                    saveAndFlush(r);
                }
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
