package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Objects;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.UniquePropertyValidator;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListRiverStationsPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.River;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.service.RiverService;
import org.devgateway.toolkit.persistence.service.RiverStationService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/river-station")
public class EditRiverStationPage extends AbstractEditPage<RiverStation> {

    @SpringBean
    private RiverService riverService;

    @SpringBean
    private RiverStationService riverStationService;

    public EditRiverStationPage(PageParameters parameters) {
        super(parameters);

        jpaService = riverStationService;

        setListPage(ListRiverStationsPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Select2ChoiceBootstrapFormComponent<River> river = new Select2ChoiceBootstrapFormComponent<>("river",
                new GenericPersistableJpaTextChoiceProvider<>(riverService));
        river.required();
        river.setEnabled(editForm.getModelObject().isNew());
        editForm.add(river);

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        name.getField().add(new UniquePropertyValidator<>(riverStationService,
                Objects.defaultIfNull(entityId, -1L), "name", this));
        editForm.add(name);

        TextFieldBootstrapFormComponent<Integer> alertLevel = new TextFieldBootstrapFormComponent<>("alertLevel");
        alertLevel.required();
        alertLevel.integer();
        editForm.add(alertLevel);

        deleteButton.setVisible(false);
    }
}
