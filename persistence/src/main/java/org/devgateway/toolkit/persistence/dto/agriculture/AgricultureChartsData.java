package org.devgateway.toolkit.persistence.dto.agriculture;

import org.devgateway.toolkit.persistence.dto.CommonConfig;

/**
 * @author Octavian Ciubotaru
 */
public class AgricultureChartsData {

    private final CommonConfig commonConfig;

    private final AgricultureConfig agricultureConfig;

    public AgricultureChartsData(CommonConfig commonConfig,
            AgricultureConfig agricultureConfig) {
        this.commonConfig = commonConfig;
        this.agricultureConfig = agricultureConfig;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public AgricultureConfig getAgricultureConfig() {
        return agricultureConfig;
    }
}
