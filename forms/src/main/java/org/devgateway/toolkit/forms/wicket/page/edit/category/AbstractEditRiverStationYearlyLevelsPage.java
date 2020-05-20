package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.DownloadRiverLevelsLink;
import org.devgateway.toolkit.forms.wicket.providers.HydrologicalYearRangeChoiceProvider;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Octavian Ciubotaru
 */
public class AbstractEditRiverStationYearlyLevelsPage
        <T extends GenericPersistable & IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends AbstractEditPage<T> {

    private final Logger logger = LoggerFactory.getLogger(AbstractEditRiverStationYearlyLevelsPage.class);

    private List<FileMetadata> uploads = new ArrayList<>();

    private FileInputBootstrapFormComponent upload;

    private final SerializableBiFunction<MonthDay, BigDecimal, L> creator;

    protected Select2ChoiceBootstrapFormComponent<HydrologicalYear> year;

    public AbstractEditRiverStationYearlyLevelsPage(PageParameters parameters,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator) {
        super(parameters);

        this.creator = creator;
    }

    protected void onInitialize(int minYear) {
        TextFieldBootstrapFormComponent<String> river = new TextFieldBootstrapFormComponent<>("station.river.name");
        river.getField().setEnabled(false);
        editForm.add(river);

        TextFieldBootstrapFormComponent<String> station = new TextFieldBootstrapFormComponent<>("station.name");
        station.getField().setEnabled(false);
        editForm.add(station);

        year = new Select2ChoiceBootstrapFormComponent<>("year",
                new HydrologicalYearRangeChoiceProvider(minYear, HydrologicalYear.now().getYear()));
        editForm.add(year);

        upload = new FileInputBootstrapFormComponent("upload", LambdaModel.of(this::getUploads, this::setUploads));
        upload.maxFiles(1);
        upload.required();
        editForm.add(upload);

        deleteButton.setVisible(false);

        Fragment extraButtons = new Fragment("extraButtons", "rsExtraButtons", this);
        editForm.replace(extraButtons);

        DownloadRiverLevelsLink<T, L> downloadButton =
                new DownloadRiverLevelsLink<>("download", editForm.getModel(), creator);
        downloadButton.setSize(Buttons.Size.Medium);
        extraButtons.add(downloadButton);
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

                    Collection<L> levels = reader.read(inputStream, creator);

                    T entity = editForm.getModelObject();
                    entity.getLevels().clear();
                    levels.forEach(l -> entity.addLevel(l));
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
