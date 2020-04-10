package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.EditDecadalRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorListPage;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/rainfalls")
public class ListDecadalRainfallPage extends AbstractIndicatorListPage<DecadalRainfall> {
    private static final long serialVersionUID = 395649903982652614L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public ListDecadalRainfallPage(PageParameters parameters) {
        super(parameters, IndicatorType.RAINFALL);

        this.jpaService = decadalRainfallService;
        this.editPageClass = EditDecadalRainfallPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("month"), "month.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("decadal"), "decadal.value"));
        // TODO if status tracking still needed
        // columns.add(new PropertyColumn<>(new StringResourceModel("status"), "status", "status"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setPageSize(WebConstants.NO_PAGE_SIZE);

        editPageLink.setVisible(false);
    }
}
