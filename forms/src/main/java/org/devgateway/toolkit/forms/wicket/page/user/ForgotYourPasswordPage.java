package org.devgateway.toolkit.forms.wicket.page.user;

import java.time.ZonedDateTime;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.service.SendEmailService;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.repository.PersonRepository;
import org.devgateway.toolkit.persistence.service.PersonService;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(value = "/forgotPassword")
public class ForgotYourPasswordPage extends BasePage {

    @SpringBean
    private PersonService personService;

    @SpringBean
    private SendEmailService sendEmailService;

    @SpringBean
    private PersonRepository personRepository;

    public ForgotYourPasswordPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final ForgotPasswordForm form = new ForgotPasswordForm("form");
        add(form);
    }

    class ForgotPasswordForm extends BootstrapForm<Void> {

        private TextFieldBootstrapFormComponent<String> emailAddress;

        private IndicatingAjaxButton goBack;

        ForgotPasswordForm(final String componentId) {
            super(componentId);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            emailAddress = ComponentUtil.addTextField(this, "emailAddress", new Model<>());
            emailAddress.getField().add(ComponentUtil.isEmail());
            emailAddress.required();

            final Label message = new Label("message",
                    new StringResourceModel("checkMessage", ForgotYourPasswordPage.this, null));
            message.setVisibilityAllowed(false);
            message.setOutputMarkupId(true);
            add(message);

            final IndicatingAjaxButton submit = new IndicatingAjaxButton("submit",
                    new StringResourceModel("submit.label", ForgotYourPasswordPage.this, null)) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(final AjaxRequestTarget target) {
                    super.onSubmit(target);

                    final String email = emailAddress.getField().getModelObject();
                    final Person person = personService.findByEmail(email);

                    if (person != null) {
                        String recoveryToken = RandomStringUtils.random(16, true, true);
                        person.setRecoveryToken(recoveryToken);
                        person.setRecoveryTokenValidUntil(ZonedDateTime.now().plusHours(1));
                        personRepository.saveAndFlush(person);

                        sendEmailService.sendEmailRecoveryEmail(person);
                    }

                    emailAddress.setVisibilityAllowed(false);
                    setVisibilityAllowed(false);

                    message.setVisibilityAllowed(true);
                    goBack.setVisibilityAllowed(true);

                    target.add(ForgotPasswordForm.this);
                }
            };
            add(submit);

            goBack = new IndicatingAjaxButton("goBack",
                    new StringResourceModel("back", ForgotYourPasswordPage.this, null)) {

                @Override
                protected void onSubmit(final AjaxRequestTarget target) {
                    super.onSubmit(target);

                    setResponsePage(LoginPage.class);
                }
            };
            goBack.setVisibilityAllowed(false);
            goBack.setOutputMarkupId(true);
            add(goBack);
        }
    }
}
