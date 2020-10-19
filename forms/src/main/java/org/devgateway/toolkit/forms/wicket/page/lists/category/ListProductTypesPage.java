package org.devgateway.toolkit.forms.wicket.page.lists.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditProductTypePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_MARKET_EDITOR)
@MountPath(value = "/product-types")
public class ListProductTypesPage extends AbstractListPage<ProductType> {
    private static final long serialVersionUID = 7833333901951945688L;

    @SpringBean
    private ProductTypeService productTypeService;

    public ListProductTypesPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = productTypeService;
        this.editPageClass = EditProductTypePage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("label"), "label", "label"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("label", SortOrder.ASCENDING);

        editPageLink.setVisibilityAllowed(false);

        excelForm.setVisibilityAllowed(true);
    }
}
