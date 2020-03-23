package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.MicrodataLink;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by Daniel Oliva
 */
public interface MicrodataLinkService extends BaseJpaService<MicrodataLink> {

    Page<DatasetDTO> findAllDTO(Specification<MicrodataLink> spec, Pageable pageable, String lang);

}
