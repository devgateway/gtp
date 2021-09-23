package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.AnnualGTPReportRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.status.GTPAnnualReportProgress;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.DepartmentStatus;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
    public Set<Department> findDepartments() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllDepartments(startingYear);
    }

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
    public List<AnnualGTPReport> findAllWithUploadsAndDepartment(Long locationId) {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllWithUploadsAndDepartment(startingYear, locationId);
    }

    @Override
    public GTPAnnualReportProgress getProgress(Integer year) {
        Set<Department> deptsWithData = repository.findAllWithUploadsByYear(year).stream()
                .map(this::getDepartmentOrNational)
                .collect(Collectors.toSet());

        List<Department> ds = new ArrayList<>();
        ds.add(Department.NATIONAL);
        ds.addAll(departmentService.findAll());

        List<DepartmentStatus> departmentStatuses = new ArrayList<>();

        for (Department department : ds) {
            departmentStatuses.add(new DepartmentStatus(department, deptsWithData.contains(department)
                    ? DataEntryStatus.PUBLISHED
                    : DataEntryStatus.NO_DATA));
        }

        return new GTPAnnualReportProgress(departmentStatuses);
    }

    private Department getDepartmentOrNational(AnnualGTPReport report) {
        if (report.getDepartment() == null) {
            return Department.NATIONAL;
        } else {
            return report.getDepartment();
        }
    }
}
