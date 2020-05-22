package org.devgateway.toolkit.forms.wicket.page.edit.indicator.riverlevel;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.AbstractEditRiverStationYearlyLevelsPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.riverlevel.ListRiverStationYearlyLevelsPage;
import org.devgateway.toolkit.persistence.dao.indicator.RiverLevel;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.indicator.RiverStationYearlyLevelsService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR)
@MountPath(value = "/river-levels-upload")
public class EditRiverStationYearlyLevelsPage
        extends AbstractEditRiverStationYearlyLevelsPage<RiverStationYearlyLevels, RiverLevel> {

    @SpringBean
    private RiverStationYearlyLevelsService riverStationYearlyLevelsService;

    @SpringBean
    private AdminSettingsService adminSettingsService;

    public EditRiverStationYearlyLevelsPage(PageParameters parameters) {
        super(parameters, RiverLevel::new);

        this.jpaService = riverStationYearlyLevelsService;

        setListPage(ListRiverStationYearlyLevelsPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        onInitialize(adminSettingsService.getStartingYear());

        year.getField().setEnabled(false);
    }
}
