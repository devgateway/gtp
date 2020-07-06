package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractExcelListActionPanel<T extends AbstractAuditableEntity & AbstractImportableEntity>
        extends GenericPanel<T> {
    private static final long serialVersionUID = 7044589999284960240L;

    private final Class<? extends Page> editPageClass;

    public AbstractExcelListActionPanel(String id, IModel<T> model, Class<? extends Page> editPageClass) {
        super(id, model);
        this.editPageClass = editPageClass;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final PageParameters pageParameters = new PageParameters();
        pageParameters.set(WebConstants.PARAM_ID, getModelObject().getId());

        BootstrapBookmarkablePageLink<?> editPageLink =
                new BootstrapBookmarkablePageLink<>("edit", editPageClass, pageParameters, Buttons.Type.Info);

        String editResourceKey = getModelObject().isEmpty() ? "import" : "reimport";
        editPageLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Small)
                .setLabel(new StringResourceModel(editResourceKey, this, null));
        add(editPageLink);

        AbstractGeneratedExcelDownloadLink<?> downloadButton = getDownloadButton("download");
        downloadButton.setSize(Buttons.Size.Small);
        add(downloadButton);
    }

    protected abstract AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id);
}
