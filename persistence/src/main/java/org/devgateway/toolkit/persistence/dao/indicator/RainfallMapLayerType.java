package org.devgateway.toolkit.persistence.dao.indicator;

/**
 * @author Nadejda Mandrescu
 */
public enum RainfallMapLayerType {
    ABNORMAL_POLYLINE,
    ABNORMAL_POLYGON,
    CUMULATIVE_POLYLINE,
    CUMULATIVE_POLYGON;

    boolean isPolyline() {
        return this.equals(ABNORMAL_POLYLINE) || this.equals(CUMULATIVE_POLYLINE);
    }
}
