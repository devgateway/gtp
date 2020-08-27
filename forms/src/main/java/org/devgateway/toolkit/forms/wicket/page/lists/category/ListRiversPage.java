package org.devgateway.toolkit.forms.wicket.page.lists.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditRiverPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.River;
import org.devgateway.toolkit.persistence.service.category.RiverService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/rivers")
public class ListRiversPage extends AbstractListPage<River> {
    private static final long serialVersionUID = 1130257532512363332L;

    @SpringBean
    private RiverService riverService;

    public ListRiversPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = riverService;

        editPageClass = EditRiverPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("name"), "name", "name"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("name", SortOrder.ASCENDING);

        excelForm.setVisibilityAllowed(true);
    }
}
