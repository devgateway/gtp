package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.MicrodataLink;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by Daniel Oliva
 */
public interface MicrodataLinkService extends BaseJpaService<MicrodataLink> {

    Page<DatasetDTO> findAllDTO(Specification<MicrodataLink> spec, Pageable pageable, String lang);

}