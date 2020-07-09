package org.devgateway.toolkit.forms.wicket.page.edit;

import static java.util.stream.Collectors.joining;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractExcelImportPage<T extends AbstractAuditableEntity & AbstractImportableEntity>
        extends AbstractEditPage<T> {
    private static final long serialVersionUID = -9188025234865194965L;

    private static final int MAX_ERRORS = 30;

    private final Logger logger = LoggerFactory.getLogger(AbstractExcelImportPage.class);

    private List<FileMetadata> uploads = new ArrayList<>();

    protected FileInputBootstrapFormComponent upload;

    protected BootstrapAjaxLink<?> download;

    protected BootstrapAjaxLink<?> downloadTemplate;

    public AbstractExcelImportPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        upload = new FileInputBootstrapFormComponent("upload", LambdaModel.of(this::getUploads, this::setUploads));
        upload.maxFiles(1);
        upload.allowedFileExtensions("xlsx");
        upload.required();
        editForm.add(upload);

        Fragment extraButtons = new Fragment("extraButtons", "excelExtraButtons", this);
        editForm.replace(extraButtons);

        download = getDownloadButton("download", false);
        download.setSize(Buttons.Size.Medium);
        download.setVisibilityAllowed(!editForm.getModelObject().isEmpty());
        extraButtons.add(download);

        downloadTemplate = getDownloadButton("downloadTemplate", true);
        downloadTemplate.setSize(Buttons.Size.Medium);
        extraButtons.add(downloadTemplate);
    }

    protected abstract BootstrapAjaxLink<?> getDownloadButton(String id, boolean template);

    private class ExcelValidatorAndImporter extends AbstractFormValidator {
        private static final long serialVersionUID = -9201030530968246406L;

        @Override
        public FormComponent<?>[] getDependentFormComponents() {
            return new FormComponent[0];
        }

        @Override
        public void validate(Form<?> form) {
            if (uploads.size() == 1) {
                FileMetadata fileMetadata = uploads.get(0);

                ByteArrayInputStream inputStream = new ByteArrayInputStream(fileMetadata.getContent().getBytes());

                try {
                    importData(inputStream);
                } catch (ReaderException e) {
                    if (e.getCause() != null) {
                        logger.warn("Import failed.", e.getCause());
                    }

                    String errors = e.getErrors().stream().limit(MAX_ERRORS).collect(joining("<br>"));
                    if (e.getErrors().size() > MAX_ERRORS) {
                        errors += "<br>Other " + (e.getErrors().size() - MAX_ERRORS) + " errors are not displayed.";
                    }

                    upload.error(new NotificationMessage(Model.of(errors)).escapeModelStrings(false));
                }
            }
        }
    }

    protected abstract void importData(InputStream inputStream) throws ReaderException;

    public Collection<FileMetadata> getUploads() {
        return uploads;
    }

    public void setUploads(Collection<FileMetadata> uploads) {
        this.uploads = new ArrayList<>(uploads);
    }
}
