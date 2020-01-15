package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dto.AgriculturalContentDTO;
import org.devgateway.toolkit.persistence.repository.AgriculturalContentRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class AgriculturalContentServiceImpl extends BaseJpaServiceImpl<AgriculturalContent>
        implements AgriculturalContentService {

    @Autowired
    private AgriculturalContentRepository repository;


    @Override
    protected BaseJpaRepository<AgriculturalContent, Long> repository() {
        return repository;
    }

    @Override
    public AgriculturalContent newInstance() {
        return new AgriculturalContent();
    }

    @Override
    public List<AgriculturalContentDTO> findPublishedContent(final String lang) {
        return repository.findPublishedContent().stream()
                .map(a -> new AgriculturalContentDTO(a, lang)).collect(Collectors.toList());
    }

    public Page<AgriculturalContentDTO> findByContentType(final String lang, final int type, final Pageable pageable) {
        List<AgriculturalContentDTO> list = repository.findByContentTypeTypeOrderByPublicationDate(type)
                .stream()
                .filter(a -> a.getPublicationDate().before(new Date()))
                .map(a -> new AgriculturalContentDTO(a, lang)).collect(Collectors.toList());
        int start = Long.valueOf(pageable.getOffset()).intValue();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? Long.valueOf(pageable.getOffset()
                + pageable.getPageSize()).intValue() : list.size();
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
