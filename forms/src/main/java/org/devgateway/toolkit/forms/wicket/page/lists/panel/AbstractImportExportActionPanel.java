package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractImportExportActionPanel<T extends AbstractAuditableEntity & AbstractImportableEntity>
        extends GenericPanel<T> {
    private static final long serialVersionUID = -1572251977463799940L;

    private final Class<? extends Page> uploadPageClass;
    private final Class<? extends Page> editPageClass;
    protected boolean downloadEmpty = true;

    public AbstractImportExportActionPanel(String id, IModel<T> model, Class<? extends Page> uploadPageClass) {
        this(id, model, uploadPageClass, null);
    }

    public AbstractImportExportActionPanel(String id, IModel<T> model, Class<? extends Page> uploadPageClass,
            Class<? extends Page> editPageClass) {
        super(id, model);
        this.uploadPageClass = uploadPageClass;
        this.editPageClass = editPageClass;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final PageParameters pageParameters = new PageParameters();
        pageParameters.set(WebConstants.PARAM_ID, getModelObject().getId());

        addEditFormButton(pageParameters);

        BootstrapBookmarkablePageLink<?> uploadPageLink =
                new BootstrapBookmarkablePageLink<>("upload", uploadPageClass, pageParameters, Buttons.Type.Info);

        String editResourceKey = getModelObject().isEmpty() ? "import" : "reimport";
        uploadPageLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Small)
                .setLabel(new StringResourceModel(editResourceKey, this, null));
        add(uploadPageLink);

        if (downloadEmpty || !getModelObject().isEmpty()) {
            BootstrapAjaxLink<?> downloadButton = getBootstrapDownloadButton("download");
            downloadButton.setSize(Buttons.Size.Small);
            add(downloadButton);
        } else {
            add(new WebMarkupContainer("download").setVisible(false));
        }
    }

    protected void addEditFormButton(PageParameters pageParameters) {
        if (editPageClass != null) {
            BootstrapBookmarkablePageLink<org.apache.poi.ss.formula.functions.T> editPageLink =
                    new BootstrapBookmarkablePageLink<>("edit", editPageClass, pageParameters, Buttons.Type.Info);
            editPageLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Small)
                    .setLabel(new StringResourceModel("edit"));
            add(editPageLink);
        } else {
            add(new WebMarkupContainer("edit").setVisible(false));
        }
    }

    protected abstract BootstrapAjaxLink<?> getBootstrapDownloadButton(String id);
}
