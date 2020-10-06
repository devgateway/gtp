package org.devgateway.toolkit.persistence.service.cnsc.menu;

import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCMenuGroup;
import org.devgateway.toolkit.persistence.repository.cnsc.menu.CNSCHeaderRepository;
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
        return null;
    }

    @Override
    public CNSCHeader get() {
        List<CNSCHeader> all = cnscHeaderRepository.findAll();
        CNSCHeader header = all.isEmpty() ? null : all.iterator().next();
        if (header == null) {
            header = new CNSCHeader();
            header.setMenu(new CNSCMenuGroup(CNSCMenuGroup.ROOT, CNSCMenuGroup.ROOT));
            header = cnscHeaderRepository.saveAndFlush(header);
        }
        return header;
    }
}
