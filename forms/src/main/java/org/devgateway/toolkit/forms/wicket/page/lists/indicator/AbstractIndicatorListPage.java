package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import java.io.Serializable;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.behaviors.AuthorizeIndicatorTypeBehavior;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.IndicatorType;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractIndicatorListPage<T extends GenericPersistable & Serializable>
        extends AbstractListPage<T> {
    private static final long serialVersionUID = 6197757854327171468L;

    protected IndicatorType indicatorType;

    public AbstractIndicatorListPage(PageParameters parameters, IndicatorType indicatorType) {
        super(parameters, false);
        this.indicatorType = indicatorType;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new AuthorizeIndicatorTypeBehavior(indicatorType));
    }
}
