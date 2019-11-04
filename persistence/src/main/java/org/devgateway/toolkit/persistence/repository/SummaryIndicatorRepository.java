package org.devgateway.toolkit.persistence.repository;


import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dto.AOISummary;
import org.devgateway.toolkit.persistence.dto.AgriculturalWomenSummary;
import org.devgateway.toolkit.persistence.dto.FoodLossSummary;
import org.devgateway.toolkit.persistence.dto.PovertySummary;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by dbianco
 */
public interface SummaryIndicatorRepository {

    List<PovertySummary> getPovertyByYearAndRegion(final Specification<PovertyIndicator> spec);

    List<AgriculturalWomenSummary> getAgriculturalWomenIndicator(final Specification<AgriculturalWomenIndicator> spec);

    List<AOISummary> getAOIIndicator(final Specification<AgricultureOrientationIndexIndicator> spec);

    List<FoodLossSummary> getFoodLossIndicator(Specification<FoodLossIndicator> spec);
}
