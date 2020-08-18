package org.devgateway.toolkit.forms.wicket.page.lists.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditLivestockDiseasePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.service.category.LivestockDiseaseService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/livestock-diseases")
public class ListLivestockDiseasePage extends AbstractListPage<LivestockDisease> {
    private static final long serialVersionUID = -2401158143495970745L;

    @SpringBean
    private LivestockDiseaseService livestockDiseaseService;

    public ListLivestockDiseasePage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = livestockDiseaseService;
        this.editPageClass = EditLivestockDiseasePage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("label"), "label", "label"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("label", SortOrder.ASCENDING);
    }
}
