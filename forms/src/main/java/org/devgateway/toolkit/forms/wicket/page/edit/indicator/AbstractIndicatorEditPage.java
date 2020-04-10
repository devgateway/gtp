package org.devgateway.toolkit.forms.wicket.page.edit.indicator;


import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.page.Homepage;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;

import java.io.Serializable;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractIndicatorEditPage<T extends GenericPersistable & Serializable>
        extends AbstractEditPage<T> {
    private static final long serialVersionUID = 4591261587496430745L;

    @SpringBean
    protected IndicatorMetadataService indicatorMetadataService;

    protected IndicatorType indicatorType;

    public AbstractIndicatorEditPage(PageParameters parameters, IndicatorType indicatorType) {
        super(parameters);
        this.indicatorType = indicatorType;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findOneByType(indicatorType);
        if (!SecurityUtil.canCurrentUserAccess(indicatorMetadata)) {
            setResponsePage(Homepage.class);
        }
    }
}
