package org.devgateway.toolkit.persistence.dto.livestock;

import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;

import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class LivestockConfig {

    private final List<LivestockDisease> diseases;

    public LivestockConfig(List<LivestockDisease> diseases) {
        this.diseases = diseases;
    }

    public List<LivestockDisease> getDiseases() {
        return diseases;
    }
}
