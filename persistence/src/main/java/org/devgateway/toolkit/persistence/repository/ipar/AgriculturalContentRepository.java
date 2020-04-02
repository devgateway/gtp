package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalContent;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface AgriculturalContentRepository extends BaseJpaRepository<AgriculturalContent, Long> {

    @Query("select p from AgriculturalContent p join p.contentType as g where p.publicationDate <= current_date()"
            + "order by p.publicationDate desc")
    List<AgriculturalContent> findPublishedContent();


    List<AgriculturalContent> findByContentTypeTypeOrderByPublicationDateDesc(int type);
}