package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditStatusEntityPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListYearlyRainfallPage;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rainfall")
public class EditYearlyRainfallPage extends AbstractEditStatusEntityPage<YearlyRainfall> {
    private static final long serialVersionUID = -3891555750902393986L;

    @SpringBean
    private YearlyRainfallService yearlyRainfallService;

    public EditYearlyRainfallPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = yearlyRainfallService;
        setListPage(ListYearlyRainfallPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        /*
        DecadalRainfallTableViewPanel decadalRainfallTableViewPanel = new DecadalRainfallTableViewPanel("rainfall",
                editForm.getModel());
        editForm.add(decadalRainfallTableViewPanel);

        deleteButton.setVisible(false);
        */
    }
}
