package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractStatusableExcelImportPage
        <T extends AbstractStatusAuditableEntity & AbstractImportableEntity> extends AbstractExcelImportPage<T>{
    private static final long serialVersionUID = -2629356746271144152L;

    public AbstractStatusableExcelImportPage(PageParameters parameters) {
        super(parameters);
    }
}
