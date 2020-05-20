package org.devgateway.toolkit.forms.wicket.page.edit.indicator;


import java.io.Serializable;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.behaviors.AuthorizeIndicatorTypeBehavior;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditStatusEntityPage;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.IndicatorType;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractIndicatorEditPage<T extends AbstractStatusAuditableEntity & Serializable>
        extends AbstractEditStatusEntityPage<T> {
    private static final long serialVersionUID = 4591261587496430745L;

    protected IndicatorType indicatorType;

    public AbstractIndicatorEditPage(PageParameters parameters, IndicatorType indicatorType) {
        super(parameters);
        this.indicatorType = indicatorType;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new AuthorizeIndicatorTypeBehavior(indicatorType));
    }
}
