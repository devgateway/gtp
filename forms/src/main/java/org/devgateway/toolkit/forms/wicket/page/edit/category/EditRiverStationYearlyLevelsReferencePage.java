package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.ByteArrayInputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListRiverStationYearlyLevelsReferencesPage;
import org.devgateway.toolkit.forms.wicket.providers.HydrologicalYearRangeChoiceProvider;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.RiverLevelReference;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.service.RiverStationYearlyLevelsReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/reference-river-levels-upload")
public class EditRiverStationYearlyLevelsReferencePage extends AbstractEditPage<RiverStationYearlyLevelsReference> {

    private static final Logger logger = LoggerFactory.getLogger(EditRiverStationYearlyLevelsReferencePage.class);

    @SpringBean
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    private List<FileMetadata> uploads = new ArrayList<>();

    private FileInputBootstrapFormComponent upload;

    public EditRiverStationYearlyLevelsReferencePage(PageParameters parameters) {
        super(parameters);

        jpaService = riverStationYearlyLevelsReferenceService;

        setListPage(ListRiverStationYearlyLevelsReferencesPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        Select2ChoiceBootstrapFormComponent<HydrologicalYear> year =
                new Select2ChoiceBootstrapFormComponent<>("year",
                        new HydrologicalYearRangeChoiceProvider(1964, Year.now().getValue()));
        year.required();
        year.getField().add(new UniqueValidator());
        editForm.add(year);

        upload = new FileInputBootstrapFormComponent("upload", LambdaModel.of(this::getUploads, this::setUploads));
        upload.maxFiles(1);
        upload.required();
        editForm.add(upload);

        deleteButton.setVisible(false);
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

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new CustomSaveEditPageButton("save",
                new StringResourceModel("saveButton", this, null));
    }

    private class CustomSaveEditPageButton extends SaveEditPageButton {

        CustomSaveEditPageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void validate() {
            super.validate();

            if (uploads.size() == 1) {
                FileMetadata fileMetadata = uploads.get(0);

                ByteArrayInputStream inputStream = new ByteArrayInputStream(fileMetadata.getContent().getBytes());

                try {
                    RiverLevelReader reader = new RiverLevelReader();

                    HydrologicalYear year = editForm.getModelObject().getYear();

                    Collection<RiverLevelReference> levels = reader.read(year, inputStream, RiverLevelReference::new);

                    RiverStationYearlyLevelsReference entity = editForm.getModelObject();
                    entity.getLevels().clear();
                    levels.forEach(entity::addLevel);
                } catch (RiverLevelReaderException e) {
                    logger.warn("Could not parse river levels.", e);
                    e.getErrors().forEach(upload::error);
                }
            }
        }
    }

    public Collection<FileMetadata> getUploads() {
        return uploads;
    }

    public void setUploads(Collection<FileMetadata> uploads) {
        this.uploads = new ArrayList<>(uploads);
    }
}
