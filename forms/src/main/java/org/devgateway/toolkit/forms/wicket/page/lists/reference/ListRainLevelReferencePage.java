package org.devgateway.toolkit.forms.wicket.page.lists.reference;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RainLevelReferenceFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall.EditRainLevelReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.RainLevelReferenceActionPanel;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rain-level-references")
public class ListRainLevelReferencePage extends AbstractListPage<RainLevelReference> {
    private static final long serialVersionUID = 2287706429777900253L;

    @SpringBean
    private RainLevelReferenceService rainLevelReferenceService;

    public ListRainLevelReferencePage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = rainLevelReferenceService;
        this.editPageClass = EditRainLevelReferencePage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("referenceYearStart"), "referenceYearStart"));
        columns.add(new PropertyColumn<>(new StringResourceModel("referenceYearEnd"), "referenceYearEnd"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.dataProvider.setSort("referenceYearEnd", SortOrder.DESCENDING);

        editPageLink.setVisible(false);
    }

    @Override
    public JpaFilterState<RainLevelReference> newFilterState() {
        return new RainLevelReferenceFilterState();
    }

    @Override
    public Panel getActionPanel(final String id, final IModel<RainLevelReference> model) {
        return new RainLevelReferenceActionPanel(id, model);
    }
}
