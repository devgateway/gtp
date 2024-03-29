package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainseason;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.GenericSleepFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.LocalDateFixedYearFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditStatusEntityPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason.ListRainSeasonPage;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_SEASON_EDITOR)
@MountPath(value = "/rain-season")
public class EditRainSeasonPage extends AbstractEditStatusEntityPage<PluviometricPostRainSeason> {
    private static final long serialVersionUID = -7281354183801334756L;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    public EditRainSeasonPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = pluviometricPostRainSeasonService;
        setListPage(ListRainSeasonPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PluviometricPostRainSeason postRainSeason = editForm.getModelObject();

        if (postRainSeason.isDeleted()) {
            scheduleRedirect();
        }
        deleteButton.setVisible(false);

        editForm.add(new GenericSleepFormComponent<>("department", new PropertyModel<>(editForm.getModel(),
                "pluviometricPost.department")));

        editForm.add(new GenericSleepFormComponent<>("pluviometricPost"));

        Integer year = postRainSeason.getRainSeason().getYear();
        LocalDateFixedYearFieldBootstrapFormComponent startDate =
                new LocalDateFixedYearFieldBootstrapFormComponent("startDate", year);
        startDate.required();
        editForm.add(startDate);
    }
}
