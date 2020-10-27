package org.devgateway.toolkit.persistence.dto.rainfallMap;

/**
 * @author Nadejda Mandrescu
 */
public class RainMap {

    private final RainMapConfig config;
    private final RainMapFilter filter;

    public RainMap(RainMapConfig config, RainMapFilter filter) {
        this.config = config;
        this.filter = filter;
    }

    public RainMapConfig getConfig() {
        return config;
    }

    public RainMapFilter getFilter() {
        return filter;
    }
}
