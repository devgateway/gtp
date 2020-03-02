package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dto.GisDTO;

import java.util.List;

public interface GisIndicatorRegion<T extends GisDTO> {

    List<T> findAllGisByRegion();

}
