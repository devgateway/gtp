package org.devgateway.toolkit.persistence.dto;

/**
 * @author Nadejda Mandrescu
 */
public class GTPMaterialsData {

    private final GTPMaterialsConfig config;

    private final GTPMaterials data;

    private final GTPMaterialsFilter filter;

    public GTPMaterialsData(GTPMaterialsConfig config, GTPMaterialsFilter filter, GTPMaterials data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public GTPMaterialsConfig getConfig() {
        return config;
    }

    public GTPMaterialsFilter getFilter() {
        return filter;
    }

    public GTPMaterials getData() {
        return data;
    }
}
