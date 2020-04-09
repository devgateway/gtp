package org.devgateway.toolkit.forms.wicket.page.lists.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditPluviometricPostPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/pluviometric-posts")
public class ListPluviometricPostPage extends AbstractListPage<PluviometricPost> {
    private static final long serialVersionUID = 797437686516787042L;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    public ListPluviometricPostPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = pluviometricPostService;
        this.editPageClass = EditPluviometricPostPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "department.name", "department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("label"), "label", "label"));
        columns.add(new PropertyColumn<>(new StringResourceModel("latitude"), "latitude", "latitude"));
        columns.add(new PropertyColumn<>(new StringResourceModel("longitude"), "longitude", "longitude"));
    }
}
