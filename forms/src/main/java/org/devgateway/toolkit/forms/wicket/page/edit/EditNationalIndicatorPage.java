package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.CheckBoxPickerBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.panel.YearValuePanel;
import org.devgateway.toolkit.forms.wicket.page.lists.ListNationalIndicatorFormPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.NationalIndicator;
import org.devgateway.toolkit.persistence.dao.categories.IndicatorGroup;
import org.devgateway.toolkit.persistence.repository.category.IndicatorGroupRepository;
import org.devgateway.toolkit.persistence.service.NationalIndicatorService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/editNationalIndicator")
public class EditNationalIndicatorPage extends AbstractEditPage<NationalIndicator>  {
    private static final long serialVersionUID = -111L;

    @SpringBean
    private NationalIndicatorService service;

    @SpringBean
    private IndicatorGroupRepository indicatorGroupRepository;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;

    public EditNationalIndicatorPage(PageParameters parameters) {
        super(parameters);
        this.jpaService = service;
        this.listPageClass = ListNationalIndicatorFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(name);

        TextFieldBootstrapFormComponent<String> description = new TextFieldBootstrapFormComponent<>("description");
        description.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(description);

        TextFieldBootstrapFormComponent<String> nameFr = new TextFieldBootstrapFormComponent<>("nameFr");
        nameFr.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(nameFr);
        nameFr.required();

        TextFieldBootstrapFormComponent<String> descriptionFr = new TextFieldBootstrapFormComponent<>("descriptionFr");
        descriptionFr.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(descriptionFr);
        descriptionFr.required();

        List<IndicatorGroup> indicatorGroups = indicatorGroupRepository.findAllFetchingLocalizedLabels();
        GenericChoiceProvider<IndicatorGroup> choiceProvider =
                new GenericChoiceProvider<IndicatorGroup>(indicatorGroups) {
            @Override
            public String getDisplayValue(IndicatorGroup indicatorGroup) {
                return indicatorGroup.getLabelFr() + " / " + indicatorGroup.getLabel();
            }
        };

        Select2ChoiceBootstrapFormComponent<IndicatorGroup> indicatorGroup =
                new Select2ChoiceBootstrapFormComponent<>("indicatorGroup", choiceProvider);
        editForm.add(indicatorGroup);
        indicatorGroup.required();

        final CheckBoxPickerBootstrapFormComponent descending = new CheckBoxPickerBootstrapFormComponent("descending");
        editForm.add(descending);

        TextFieldBootstrapFormComponent<String> source = new TextFieldBootstrapFormComponent<>("source");
        source.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(source);

        TextFieldBootstrapFormComponent<String> link = new TextFieldBootstrapFormComponent<>("link");
        link.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        link.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(link);

        TextFieldBootstrapFormComponent<Double> referenceYear = new TextFieldBootstrapFormComponent<>("referenceYear");
        editForm.add(referenceYear);

        TextFieldBootstrapFormComponent<Double> referenceValue =
                new TextFieldBootstrapFormComponent<>("referenceValue");
        editForm.add(referenceValue);

        TextFieldBootstrapFormComponent<Double> targetYear = new TextFieldBootstrapFormComponent<>("targetYear");
        editForm.add(targetYear);

        TextFieldBootstrapFormComponent<Double> targetValue = new TextFieldBootstrapFormComponent<>("targetValue");
        editForm.add(targetValue);

        TextFieldBootstrapFormComponent<String> measure = new TextFieldBootstrapFormComponent<>("measure");
        measure.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(measure);
        measure.required();

        editForm.add(new YearValuePanel("yearValue"));
        editForm.add(new Label("yearValueText", new StringResourceModel("yearValueText", this, null)));

        final CheckBoxPickerBootstrapFormComponent approved = new CheckBoxPickerBootstrapFormComponent("approved");
        if (!isAdmin()) {
            approved.setOutputMarkupPlaceholderTag(true);
            approved.setVisible(false);
        }
        editForm.add(approved);

        if (entityId != null && ((NationalIndicator) editForm.getModelObject()).isApproved()
                && !isAdmin()) {
            deleteButton.setVisibilityAllowed(false);
        }
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                NationalIndicator model = editForm.getModelObject();
                boolean isOk = true;
                redirectToSelf = false;
                if (model.getYearValue().size() > 1) {
                    Set<Integer> years = new HashSet<>();
                    for (int i = 0; i < model.getYearValue().size(); i++) {
                        if (!years.add(model.getYearValue().get(i).getYear())) {
                            isOk = false;
                        }
                    }
                }
                if (!isOk) {
                    feedbackPanel.error(new StringResourceModel("yearError", this, null).getString());
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    if (model.getId() == null) {
                        model.setUploadedBy(SecurityUtil.getCurrentAuthenticatedPerson());
                    }
                    jpaService.saveAndFlush(model);
                    markupCacheService.clearAllCaches();
                    cacheService.releaseCache();
                }
                redirect(target);
            }


            private void redirect(final AjaxRequestTarget target) {
                if (redirectToSelf) {
                    // we need to close the blockUI if it's opened and enable all
                    // the buttons
                    target.appendJavaScript("$.unblockUI();");
                } else if (redirect) {
                    setResponsePage(getResponsePage(), getParameterPage());
                }
            }
        };
    }
}
