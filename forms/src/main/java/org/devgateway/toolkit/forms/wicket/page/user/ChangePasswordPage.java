package org.devgateway.toolkit.forms.wicket.page.user;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/changePassword")
public class ChangePasswordPage extends EditUserPage {

    public ChangePasswordPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.getModelObject().setChangeProfilePassword(true);

        username.setVisibilityAllowed(false);
        firstName.setVisibilityAllowed(false);
        lastName.setVisibilityAllowed(false);
        email.setVisibilityAllowed(false);
        phone.setVisibilityAllowed(false);
        title.setVisibilityAllowed(false);
        organization.setVisibilityAllowed(false);
        roles.setVisibilityAllowed(false);
        enabled.setVisibilityAllowed(false);
        changePasswordNextSignIn.setVisibilityAllowed(false);
        changeProfilePassword.setVisibilityAllowed(false);
    }

    @Override
    protected boolean isChangePassPage() {
        return true;
    }
}
