package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dto.AgriculturalContentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgriculturalContentService extends BaseJpaService<AgriculturalContent> {

    List<AgriculturalContentDTO> findPublishedContent(String lang);

    Page<AgriculturalContentDTO> findByContentType(String lang, int type, Pageable pageable);

}
