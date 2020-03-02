package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.Homepage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.GisSettings;
import org.devgateway.toolkit.persistence.service.GisSettingsService;
import org.devgateway.toolkit.persistence.service.RegionIndicatorService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;
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
    private RegionIndicatorService regionIndicatorService;

    public EditGisSettingsPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = Homepage.class;

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

        editForm.add(new Label("gisTitle", new StringResourceModel("gisTitle", this, null)));

        List<String> indicatorList = regionIndicatorService.findGisRegionIndicators(null)
                .stream().map(n -> n.getNameEnFr()).collect(Collectors.toList());

        GenericChoiceProvider<String> choiceProvider = new GenericChoiceProvider<String>(indicatorList) {
            @Override
            public String getDisplayValue(String indicator) {
                return indicator;
            }
        };

        Select2ChoiceBootstrapFormComponent<String> rightGisName =
                new Select2ChoiceBootstrapFormComponent<>("rightGisName", choiceProvider);
        editForm.add(rightGisName);

        Select2ChoiceBootstrapFormComponent<String> leftGisName =
                new Select2ChoiceBootstrapFormComponent<>("leftGisName", choiceProvider);
        editForm.add(leftGisName);
    }
}
