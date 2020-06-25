package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRiverStationYearlyLevelsReferencesPage;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.reference.RiverLevelReference;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.service.reference.RiverStationYearlyLevelsReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/reference-river-levels-upload")
public class EditRiverStationYearlyLevelsReferencePage
        extends AbstractEditRiverStationYearlyLevelsPage<RiverStationYearlyLevelsReference, RiverLevelReference> {

    @SpringBean
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    public EditRiverStationYearlyLevelsReferencePage(PageParameters parameters) {
        super(parameters, RiverStationYearlyLevelsReference::new, RiverLevelReference::new);

        jpaService = riverStationYearlyLevelsReferenceService;

        setListPage(ListRiverStationYearlyLevelsReferencesPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        onInitialize(1964);

        year.getField().add(new UniqueValidator());
        year.required();
    }

    private class UniqueValidator implements IValidator<HydrologicalYear> {

        @Override
        public void validate(final IValidatable<HydrologicalYear> validatable) {
            final HydrologicalYear value = validatable.getValue();
            RiverStation station = editForm.getModelObject().getStation();
            Long id = editForm.getModelObject().getId();
            if (riverStationYearlyLevelsReferenceService.exists(station, value, id)) {
                ValidationError error = new ValidationError(this);
                error.setVariable("year", value);
                validatable.error(error);
            }
        }
    }
}
