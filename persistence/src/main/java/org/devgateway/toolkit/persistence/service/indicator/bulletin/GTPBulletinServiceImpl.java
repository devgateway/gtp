package org.devgateway.toolkit.persistence.service.indicator.bulletin;

import static java.util.stream.Collectors.toList;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.GTPBulletinRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class GTPBulletinServiceImpl extends BaseJpaServiceImpl<GTPBulletin> implements GTPBulletinService {

    @Autowired
    private GTPBulletinRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    protected BaseJpaRepository<GTPBulletin, Long> repository() {
        return repository;
    }

    @Override
    public GTPBulletin newInstance() {
        return new GTPBulletin();
    }

    @Override
    public List<Integer> findYears() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllYears().stream().filter(y -> y >= startingYear).sorted().collect(toList());
    }

    @Override
    public Set<Department> findDepartments() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllDepartments(startingYear);
    }

    @Override
    @Transactional
    public void generate() {
        Integer startingYear = adminSettingsService.getStartingYear();
        LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
        int currentYear = now.getYear();

        Set<GTPBulletin> byYmdl = new TreeSet<>(Comparator.comparing(GTPBulletin::getYear)
                .thenComparing(GTPBulletin::getMonth)
                .thenComparing(GTPBulletin::getDecadal)
                .thenComparing((a, b) -> Department.compareTo(a.getDepartment(), b.getDepartment())));
        byYmdl.addAll(findAll());

        List<Department> ds = departmentService.findAll();
        // National
        ds.add(null);

        List<GTPBulletin> newBs = new ArrayList<>();

        for (int y = startingYear; y <= currentYear; y++) {
            for (Month month : GTPBulletin.MONTHS) {
                for (Decadal decadal : Decadal.values()) {
                    if (!isFuture(y, month, decadal)) {
                        for (Department d : ds) {
                            GTPBulletin bulletin = new GTPBulletin(y, month, decadal, d);
                            if (!byYmdl.contains(bulletin)) {
                                newBs.add(bulletin);
                            }
                        }
                    }
                }
            }
        }
        if (!newBs.isEmpty()) {
            repository.saveAll(newBs);
        }

    }

    private boolean isFuture(int y, Month month, Decadal decadal) {
        LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
        return y > now.getYear()
                || (y == now.getYear() && (month.compareTo(now.getMonth()) > 0
                || month.compareTo(now.getMonth()) == 0
                && decadal.getValue() > Decadal.fromDayOfMonth(now.getDayOfMonth()).getValue()));
    }

    @Override
    public List<GTPBulletin> findAllWithUploadsAndLocation(Long locationId) {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllWithUploadsAndDepartment(startingYear, locationId);
    }
}
