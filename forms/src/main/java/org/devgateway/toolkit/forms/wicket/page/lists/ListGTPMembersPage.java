package org.devgateway.toolkit.forms.wicket.page.lists;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.EditGTPMemberPage;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.service.GTPMemberService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/gtp-members")
public class ListGTPMembersPage extends AbstractListPage<GTPMember> {
    private static final long serialVersionUID = -2632501305883039609L;

    @SpringBean
    private GTPMemberService gtpMemberService;

    public ListGTPMembersPage(PageParameters parameters) {
        super(parameters, false);

        editPageClass = EditGTPMemberPage.class;
        jpaService = gtpMemberService;

        columns.add(new LambdaColumn<>(new StringResourceModel("name", this), "name", GTPMember::getName));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        excelForm.setVisibilityAllowed(true);
    }
}
