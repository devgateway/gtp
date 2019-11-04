package org.devgateway.toolkit.persistence.repository;


import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dto.AgriculturalWomenSummary;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by dbianco
 */
public interface SummaryIndicatorRepository {

    List getPovertyByYearAndRegion(final Specification<PovertyIndicator> spec);

    List<AgriculturalWomenSummary> getAgriculturalWomenIndicator(final Specification<AgriculturalWomenIndicator> spec);
}
