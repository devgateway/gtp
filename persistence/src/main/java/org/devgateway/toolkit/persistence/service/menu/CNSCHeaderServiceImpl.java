package org.devgateway.toolkit.persistence.service.menu;

import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.repository.menu.CNSCHeaderRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class CNSCHeaderServiceImpl extends BaseJpaServiceImpl<CNSCHeader> implements CNSCHeaderService {

    @Autowired
    private CNSCHeaderRepository cnscHeaderRepository;

    @Override
    protected BaseJpaRepository<CNSCHeader, Long> repository() {
        return cnscHeaderRepository;
    }

    @Override
    public CNSCHeader newInstance() {
        CNSCHeader header = new CNSCHeader();
        header.setMenu(new MenuGroup(MenuGroup.ROOT, "CNSC Menu"));
        return header;
    }

    @Override
    public Long getId() {
        List<CNSCHeader> all = cnscHeaderRepository.findAll();
        return all.isEmpty() ? null : all.iterator().next().getId();
    }

    @Override
    public CNSCHeader get() {
        List<CNSCHeader> all = cnscHeaderRepository.findAll();
        return all.isEmpty() ? null : all.iterator().next();
    }
}
