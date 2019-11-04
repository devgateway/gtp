package org.devgateway.toolkit.persistence.repository;


import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by dbianco
 */
public interface SummaryIndicatorRepository {

    List getPovertyByYearAndRegion(final Specification<PovertyIndicator> spec);
}
