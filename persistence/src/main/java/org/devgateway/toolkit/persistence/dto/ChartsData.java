package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;

/**
 * @author Octavian Ciubotaru
 */
public class ChartsData {

    private RainLevelChart rainLevelChart;

    public ChartsData(RainLevelChart rainLevelChart) {
        this.rainLevelChart = rainLevelChart;
    }

    public RainLevelChart getRainLevelChart() {
        return rainLevelChart;
    }
}
