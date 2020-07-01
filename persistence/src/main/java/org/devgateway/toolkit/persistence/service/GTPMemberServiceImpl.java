package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.repository.GTPMemberRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class GTPMemberServiceImpl extends BaseJpaServiceImpl<GTPMember> implements GTPMemberService {

    @Autowired
    private GTPMemberRepository repository;

    @Override
    protected BaseJpaRepository<GTPMember, Long> repository() {
        return repository;
    }

    @Override
    public GTPMember newInstance() {
        return new GTPMember();
    }
}
