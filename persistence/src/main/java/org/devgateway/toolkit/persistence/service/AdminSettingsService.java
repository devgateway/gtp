package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.AdminSettings;

import java.util.List;

public interface AdminSettingsService extends BaseJpaService<AdminSettings> {

    AdminSettings get();

    Integer getStartingYear();

    List<Integer> getYears();

}
