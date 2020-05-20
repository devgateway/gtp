package org.devgateway.toolkit.forms.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;

/**
 * @author Octavian Ciubotaru
 */
public class AuthorizeIndicatorTypeBehavior extends Behavior {

    @SpringBean
    private IndicatorMetadataService indicatorMetadataService;

    private final IndicatorType indicatorType;

    public AuthorizeIndicatorTypeBehavior(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    @Override
    public void bind(Component component) {
        Injector.get().inject(this);

        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findOneByType(indicatorType);
        if (!SecurityUtil.canCurrentUserAccess(indicatorMetadata)) {
            throw new UnauthorizedActionException(component, Component.RENDER);
        }
    }
}
