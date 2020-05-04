package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.AbstractIndicatorEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListDecadalRainfallPage;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/rainfall")
public class EditDecadalRainfallPage extends AbstractIndicatorEditPage<DecadalRainfall> {
    private static final long serialVersionUID = -3891555750902393986L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public EditDecadalRainfallPage(PageParameters parameters) {
        super(parameters, IndicatorType.RAINFALL);

        this.jpaService = decadalRainfallService;
        setListPage(ListDecadalRainfallPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DecadalRainfallTableViewPanel decadalRainfallTableViewPanel = new DecadalRainfallTableViewPanel("rainfall",
                editForm.getModel());
        editForm.add(decadalRainfallTableViewPanel);

        deleteButton.setVisible(false);
    }
}
