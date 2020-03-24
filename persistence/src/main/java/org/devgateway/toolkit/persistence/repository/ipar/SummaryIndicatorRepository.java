package org.devgateway.toolkit.persistence.repository.ipar;


import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.PovertyIndicator;
import org.devgateway.toolkit.persistence.dto.ipar.AOISummary;
import org.devgateway.toolkit.persistence.dto.ipar.AgriculturalWomenSummary;
import org.devgateway.toolkit.persistence.dto.ipar.FoodLossSummary;
import org.devgateway.toolkit.persistence.dto.ipar.PovertySummary;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by dbianco
 */
public interface SummaryIndicatorRepository {

    List<PovertySummary> getPovertyByYearAndRegionAndLevel(final Specification<PovertyIndicator> spec);

    List<AgriculturalWomenSummary> getAgriculturalWomenIndicator(final Specification<AgriculturalWomenIndicator> spec);

    List<AOISummary> getAOIIndicator(final Specification<AgricultureOrientationIndexIndicator> spec);

    List<FoodLossSummary> getFoodLossIndicator(Specification<FoodLossIndicator> spec);
}
