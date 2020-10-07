package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.MaxFileSizeValidator;
import org.devgateway.toolkit.forms.wicket.components.form.CheckBoxToggleBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.Homepage;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.MenuTree;
import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.service.menu.CNSCHeaderService;
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

        addLogo();
        addSearchURL();
        addMenuTree();
    }

    private void addLogo() {
        FileInputBootstrapFormComponent logo = new FileInputBootstrapFormComponent("logo");
        logo.maxFiles(1);
        logo.allowedFileExtensions("jpg", "jpeg", "png");
        logo.getField().add(new MaxFileSizeValidator(CNSCHeader.MAX_LOGO_SIZE, logo.getField()));
        logo.showNote();
        logo.required();
        editForm.add(logo);
        logo.getNote().add(new CssClassNameAppender("text-warning"));
    }

    private void addSearchURL() {
        TextFieldBootstrapFormComponent<String> searchUrl = new TextFieldBootstrapFormComponent<String>("searchUrl") {
            private static final long serialVersionUID = 4800229517077795963L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                boolean isEnabled = editForm.getModelObject().isSearchUrlEnabled();
                getField().setRequired(isEnabled);
                setEnabled(isEnabled);
            }
        };
        searchUrl.getField().add(StringValidator.maximumLength(CNSCHeader.SEARCH_URL_MAX_LENGTH));
        searchUrl.setOutputMarkupId(true);
        searchUrl.setOutputMarkupPlaceholderTag(true);
        editForm.add(searchUrl);

        CheckBoxToggleBootstrapFormComponent isSearchUrlEnabled =
                new CheckBoxToggleBootstrapFormComponent("isSearchUrlEnabled") {
                    private static final long serialVersionUID = 8026783538327803299L;

                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        target.add(searchUrl);
                    }
                };
        isSearchUrlEnabled.hideLabel();
        isSearchUrlEnabled.setOutputMarkupId(true);
        isSearchUrlEnabled.setOutputMarkupPlaceholderTag(true);
        editForm.add(isSearchUrlEnabled);
    }

    private void addMenuTree() {
        MenuTree treePanel = new MenuTree(Model.of(editForm.getModelObject().getMenu()));
        treePanel.setOutputMarkupId(true);
        editForm.add(treePanel);
    }

}