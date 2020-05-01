package org.devgateway.toolkit.forms.wicket.page.ipar.edit;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.Homepage;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.ipar.edit.panel.GisSettingsDescPanel;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.service.ipar.DepartmentIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.GisSettingsService;
import org.devgateway.toolkit.persistence.service.ipar.NationalIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.RegionIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.ReleaseCacheService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dbianco
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/gisSettings")
public class EditGisSettingsPage extends AbstractEditPage<GisSettings> {

    private static final long serialVersionUID = 5742724046825803877L;

    @SpringBean
    private GisSettingsService service;

    @SpringBean
    private NationalIndicatorService nationalIndicatorService;

    @SpringBean
    private RegionIndicatorService regionIndicatorService;

    @SpringBean
    private DepartmentIndicatorService departmentIndicatorService;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;

    public EditGisSettingsPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        setListPage(Homepage.class);

        if (entityId == null) {
            final List<GisSettings> listSettings = service.findAll();
            // just keep 1 entry for settings
            if (listSettings.size() == 1) {
                entityId = listSettings.get(0).getId();
            }
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.add(new Label("nationalTitle", new StringResourceModel("nationalTitle", this, null)));
        List<String> nationalList = nationalIndicatorService.findAll()
                .stream().map(n -> n.getNameEnFr()).collect(Collectors.toList());

        GenericChoiceProvider<String> nationalProvider = new GenericChoiceProvider<String>(nationalList) {
            @Override
            public String getDisplayValue(String indicator) {
                return indicator;
            }
        };

        Select2ChoiceBootstrapFormComponent<String> leftNationalName =
                new Select2ChoiceBootstrapFormComponent<>("leftNationalName", nationalProvider);
        editForm.add(leftNationalName);

        Select2ChoiceBootstrapFormComponent<String> rightNationalName =
                new Select2ChoiceBootstrapFormComponent<>("rightNationalName", nationalProvider);
        editForm.add(rightNationalName);

        ////////////////////////////

        editForm.add(new Label("gisTitle", new StringResourceModel("gisTitle", this, null)));

        List<String> regionList = regionIndicatorService.findGisRegionIndicators(null)
                .stream().map(n -> n.getNameEnFr()).collect(Collectors.toList());

        GenericChoiceProvider<String> regionProvider = new GenericChoiceProvider<String>(regionList) {
            @Override
            public String getDisplayValue(String indicator) {
                return indicator;
            }
        };

        Select2ChoiceBootstrapFormComponent<String> rightGisRegionName =
                new Select2ChoiceBootstrapFormComponent<>("rightGisRegionName", regionProvider);
        editForm.add(rightGisRegionName);

        Select2ChoiceBootstrapFormComponent<String> leftGisRegionName =
                new Select2ChoiceBootstrapFormComponent<>("leftGisRegionName", regionProvider);
        editForm.add(leftGisRegionName);


        List<String> depList = departmentIndicatorService.findGisDepartmentIndicators(null)
                .stream().map(n -> n.getNameEnFr()).collect(Collectors.toList());

        GenericChoiceProvider<String> depProvider = new GenericChoiceProvider<String>(depList) {
            @Override
            public String getDisplayValue(String indicator) {
                return indicator;
            }
        };

        Select2ChoiceBootstrapFormComponent<String> rightGisDepartmentName =
                new Select2ChoiceBootstrapFormComponent<>("rightGisDepartmentName", depProvider);
        editForm.add(rightGisDepartmentName);

        Select2ChoiceBootstrapFormComponent<String> leftGisDepartmentName =
                new Select2ChoiceBootstrapFormComponent<>("leftGisDepartmentName", depProvider);
        editForm.add(leftGisDepartmentName);

        editForm.add(new GisSettingsDescPanel("descriptions"));

    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                GisSettings model = editForm.getModelObject();
                boolean isOk = true;
                redirectToSelf = false;
                if (model.getDescriptions().size() > 1) {
                    Set<Integer> types = new HashSet<>();
                    for (int i = 0; i < model.getDescriptions().size(); i++) {
                        if (!types.add(model.getDescriptions().get(i).getGisIndicator().getType())) {
                            isOk = false;
                        }
                    }
                }
                if (!isOk) {
                    feedbackPanel.error(new StringResourceModel("typeError", this, null).getString());
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
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
                    scheduleRedirect();
                }
            }
        };
    }
}
