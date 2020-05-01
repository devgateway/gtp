package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;

import java.io.Serializable;

/**
 * Redirect to the list page if entity doesn't exist
 * @author Nadejda Mandrescu
 */
public abstract class AbstractExistingEntityEditPage<T extends GenericPersistable & Serializable>
        extends AbstractEditPage<T> {

    public AbstractExistingEntityEditPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (!isExisting()) {
            scheduleRedirect();
        }
    }
}
