package org.devgateway.toolkit.persistence.dto;

/**
 * @author Octavian Ciubotaru
 */
public class ChartConfig {

    private final String organization;

    public ChartConfig(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }
}
