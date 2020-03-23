package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.MicrodataLink;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.ipar.MicrodataLinkRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Daniel Oliva
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class MicrodataLinkServiceImpl extends BaseJpaServiceImpl<MicrodataLink> implements MicrodataLinkService {

    @Autowired
    private MicrodataLinkRepository repository;

    @Override
    protected BaseJpaRepository<MicrodataLink, Long> repository() {
        return repository;
    }

    public Page<DatasetDTO> findAllDTO(Specification<MicrodataLink> spec, Pageable pageable, String lang) {
        Page<MicrodataLink> microdataPage =  repository.findAll(spec, pageable);
        List<DatasetDTO> dtoList = microdataPage.get().map(d -> new DatasetDTO(d, lang)).collect(Collectors.toList());
        return new PageImpl(dtoList, microdataPage.getPageable(), microdataPage.getTotalElements());
    }

    @Override
    public MicrodataLink newInstance() {
        return new MicrodataLink();
    }

}
