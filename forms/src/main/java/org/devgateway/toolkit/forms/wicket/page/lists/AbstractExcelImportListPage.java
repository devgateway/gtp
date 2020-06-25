package org.devgateway.toolkit.forms.wicket.page.lists;

import java.io.Serializable;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractExcelImportListPage<T extends GenericPersistable & Serializable>
        extends AbstractListPage<T> {

    public AbstractExcelImportListPage(PageParameters parameters) {
        super(parameters, false);
    }

    @Override
    protected String getPageCssClassName() {
        return "excel-import-list";
    }
}
