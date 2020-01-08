package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dto.AgriculturalContentDTO;

import java.util.List;

public interface AgriculturalContentService extends BaseJpaService<AgriculturalContent> {

    List<AgriculturalContentDTO> findPublishedContent(String lang);

}
