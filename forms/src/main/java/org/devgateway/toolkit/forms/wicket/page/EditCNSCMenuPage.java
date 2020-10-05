package org.devgateway.toolkit.forms.wicket.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.persistence.dao.AdminSettings;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/cnsc-menu")
public class EditCNSCMenuPage extends AbstractEditPage<AdminSettings> {
    private static final long serialVersionUID = 2764329521302469397L;

    public EditCNSCMenuPage(PageParameters parameters) {
        super(parameters);

        setListPage(Homepage.class);
    }
}
