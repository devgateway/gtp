package org.devgateway.toolkit.forms.wicket.page.user;

import java.time.ZonedDateTime;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.components.form.PasswordFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@MountPath(value = "/changeForgottenPassword")
public class ChangeForgottenPasswordPage extends BasePage {

    @SpringBean
    private PersonRepository personRepository;

    @SpringBean
    private PasswordEncoder passwordEncoder;

    private String recoveryToken;

    public ChangeForgottenPasswordPage(PageParameters parameters) {
        super(parameters);

        recoveryToken = parameters.get(0).toOptionalString();

        ChangeForgottenPasswordForm form = new ChangeForgottenPasswordForm("form");
        add(form);
    }

    private class ChangeForgottenPasswordForm extends BootstrapForm<Void> {

        ChangeForgottenPasswordForm(String componentId) {
            super(componentId);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            final NotificationPanel notificationPanel = new NotificationPanel("notificationPanel");
            notificationPanel.hideAfter(Duration.seconds(15));
            notificationPanel.setOutputMarkupId(true);
            add(notificationPanel);

            final PasswordFieldBootstrapFormComponent newPasswordField = new PasswordFieldBootstrapFormComponent(
                    "newPassword", new Model<>());
            newPasswordField.getField().setResetPassword(false);
            newPasswordField.getField().add(new PasswordPatternValidator());
            newPasswordField.setOutputMarkupId(true);
            add(newPasswordField);

            final PasswordFieldBootstrapFormComponent confirmNewPasswordField = new PasswordFieldBootstrapFormComponent(
                    "confirmNewPassword", new Model<>());
            confirmNewPasswordField.getField().setResetPassword(false);
            confirmNewPasswordField.setOutputMarkupId(true);
            add(confirmNewPasswordField);

            EqualPasswordInputValidator validator = new EqualPasswordInputValidator(
                    newPasswordField.getField(),
                    confirmNewPasswordField.getField());
            add(validator);

            final Label passwordChangedMessage =  new Label("passwordChanged", new ResourceModel("passwordChanged"));
            passwordChangedMessage.setVisibilityAllowed(false);
            passwordChangedMessage.setOutputMarkupId(true);
            add(passwordChangedMessage);

            final BookmarkablePageLink goBack = new BookmarkablePageLink<>("loginLink", LoginPage.class);
            goBack.setVisibilityAllowed(false);
            goBack.setOutputMarkupId(true);
            add(goBack);

            if (StringUtils.isEmpty(recoveryToken)) {
                notificationPanel.error("Recovery token is invalid or expired.");
                setVisibilityAllowed(false);
            }

            Person person = personRepository.findByRecoveryToken(recoveryToken);

            if (person == null) {
                notificationPanel.error("Recovery token is invalid or expired.");
                setVisibilityAllowed(false);
            }

            if (person != null && person.getRecoveryTokenValidUntil().isBefore(ZonedDateTime.now())) {
                person.setRecoveryToken(null);
                person.setRecoveryTokenValidUntil(null);
                personRepository.saveAndFlush(person);

                notificationPanel.error("Recovery token is invalid or expired.");
                setVisibilityAllowed(false);
            }

            IndicatingAjaxButton submit = new IndicatingAjaxButton("submit") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    target.add(notificationPanel);

                    person.setRecoveryToken(null);
                    person.setRecoveryTokenValidUntil(null);
                    person.setPassword(passwordEncoder.encode(newPasswordField.getField().getModelObject()));
                    personRepository.saveAndFlush(person);

                    SecurityUtil.onPersonChanged(person);

                    passwordChangedMessage.setVisibilityAllowed(true);
                    goBack.setVisibilityAllowed(true);
                    setVisibilityAllowed(false);
                    newPasswordField.setVisibilityAllowed(false);
                    confirmNewPasswordField.setVisibilityAllowed(false);

                    target.add(ChangeForgottenPasswordForm.this);
                }

                @Override
                protected void onError(AjaxRequestTarget target) {
                    target.add(notificationPanel);
                }
            };
            submit.setOutputMarkupId(true);
            add(submit);
        }
    }
}
