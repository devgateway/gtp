package org.devgateway.toolkit.forms.wicket.page;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.MaxFileSizeValidator;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.service.cnsc.menu.CNSCHeaderService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/cnsc-header")
public class EditCNSCHeaderPage extends AbstractEditPage<CNSCHeader> {
    private static final long serialVersionUID = 2764329521302469397L;

    @SpringBean
    private CNSCHeaderService cnscHeaderService;

    public EditCNSCHeaderPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = cnscHeaderService;
        setListPage(Homepage.class);

        this.entityId = cnscHeaderService.get().getId();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        FileInputBootstrapFormComponent logo = new FileInputBootstrapFormComponent("logo");
        logo.maxFiles(1);
        logo.allowedFileExtensions("jpg", "jpeg", "png");
        logo.getField().add(new MaxFileSizeValidator(CNSCHeader.MAX_LOGO_SIZE, logo.getField()));
        logo.showNote();
        logo.required();
        editForm.add(logo);
        logo.getNote().add(new CssClassNameAppender("text-warning"));
    }
}
