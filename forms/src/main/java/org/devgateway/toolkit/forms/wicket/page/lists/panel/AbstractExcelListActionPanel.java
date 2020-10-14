package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractExcelListActionPanel<T extends AbstractAuditableEntity & AbstractImportableEntity>
        extends AbstractImportExportActionPanel<T> {
    private static final long serialVersionUID = 7044589999284960240L;


    public AbstractExcelListActionPanel(String id, IModel<T> model, Class<? extends Page> uploadPageClass) {
        this(id, model, uploadPageClass, null);
    }

    public AbstractExcelListActionPanel(String id, IModel<T> model, Class<? extends Page> uploadPageClass,
            Class<? extends Page> editPageClass) {
        super(id, model, uploadPageClass, editPageClass);
    }

    @Override
    protected BootstrapAjaxLink<?> getBootstrapDownloadButton(String id) {
        return getDownloadButton(id);
    }

    protected abstract AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id);
}
