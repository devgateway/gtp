package org.devgateway.toolkit.forms.wicket.page.lists.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditRiverStationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.service.category.RiverStationService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR)
@MountPath(value = "/river-stations")
public class ListRiverStationsPage extends AbstractListPage<RiverStation> {
    private static final long serialVersionUID = -297561430822698662L;

    @SpringBean
    private RiverStationService riverStationService;

    public ListRiverStationsPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = riverStationService;

        editPageClass = EditRiverStationPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("river"), "river.name", "river.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("name"), "name", "name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("alertLevel"), "alertLevel", "alertLevel"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("river.name", SortOrder.ASCENDING);

        excelForm.setVisibilityAllowed(true);
    }
}
