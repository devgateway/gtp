package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.repository.GTPBulletinRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class GTPBulletinServiceImpl extends BaseJpaServiceImpl<GTPBulletin> implements GTPBulletinService {

    @Autowired
    private GTPBulletinRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    protected BaseJpaRepository<GTPBulletin, Long> repository() {
        return repository;
    }

    @Override
    public GTPBulletin newInstance() {
        return new GTPBulletin();
    }

    public List<Integer> findYears() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllYears().stream().filter(y -> y >= startingYear).sorted().collect(toList());
    }

    @Override
    @Transactional
    public void generate() {
        Integer startingYear = adminSettingsService.getStartingYear();
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        Set<GTPBulletin> byYmd = new TreeSet<>(Comparator.comparing(GTPBulletin::getYear)
                .thenComparing(GTPBulletin::getMonth)
                .thenComparing(GTPBulletin::getDecadal));
        byYmd.addAll(findAll());

        for (int y = startingYear; y <= currentYear; y++) {
            for (Month month : GTPBulletin.MONTHS) {
                for (Decadal decadal : Decadal.values()) {
                    if (!isFuture(y, month, decadal)) {
                        GTPBulletin bulletin = new GTPBulletin(y, month, decadal);
                        if (!byYmd.contains(bulletin)) {
                            saveAndFlush(bulletin);
                        }
                    }
                }
            }
        }
    }

    private boolean isFuture(int y, Month month, Decadal decadal) {
        LocalDate now = LocalDate.now();
        return y > now.getYear()
                || (y == now.getYear() && (month.compareTo(now.getMonth()) > 0
                || month.compareTo(now.getMonth()) == 0
                && decadal.getValue() > Decadal.fromDayOfMonth(now.getDayOfMonth()).getValue()));
    }

    @Override
    public List<GTPBulletin> findAllWithUploads() {
        Integer startingYear = adminSettingsService.getStartingYear();
        return repository.findAllWithUploads(startingYear);
    }
}
