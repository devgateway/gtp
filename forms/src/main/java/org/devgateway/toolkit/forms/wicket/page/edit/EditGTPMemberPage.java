package org.devgateway.toolkit.forms.wicket.page.edit;

import java.util.Collection;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextAreaFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListGTPMembersPage;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPMember;
import org.devgateway.toolkit.persistence.service.GTPMemberService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/gtp-member")
public class EditGTPMemberPage extends AbstractEditPage<GTPMember> {

    @SpringBean
    private GTPMemberService gtpMemberService;

    public EditGTPMemberPage(PageParameters parameters) {
        super(parameters);

        setListPage(ListGTPMembersPage.class);
        jpaService = gtpMemberService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.getField().add(StringValidator.maximumLength(GTPMember.NAME_MAX_LENGTH));
        name.required();
        editForm.add(name);

        TextAreaFieldBootstrapFormComponent<String> description =
                new TextAreaFieldBootstrapFormComponent<>("description");
        description.getField().add(StringValidator.maximumLength(GTPMember.DESCRIPTION_MAX_LENGTH));
        description.required();
        editForm.add(description);

        FileInputBootstrapFormComponent logo = new FileInputBootstrapFormComponent("logo");
        logo.maxFiles(1);
        logo.allowedFileExtensions("jpg", "jpeg", "png");
        logo.getField().add(new MaxIconSize());
        logo.showNote();
        editForm.add(logo);
        logo.getNote().add(new CssClassNameAppender("text-warning"));

        TextFieldBootstrapFormComponent<String> url = new TextFieldBootstrapFormComponent<>("url");
        url.getField().add(new UrlValidator());
        editForm.add(url);
    }

    private static class MaxIconSize implements IValidator<Collection<FileMetadata>> {

        @Override
        public void validate(IValidatable<Collection<FileMetadata>> validatable) {
            validatable.getValue().stream().filter(m -> m.getSize() > GTPMember.MAX_ICON_SIZE).forEach(m -> {
                ValidationError error = new ValidationError(this);
                error.setVariable("fileName", m.getName());
                validatable.error(error);
            });
        }
    }
}
