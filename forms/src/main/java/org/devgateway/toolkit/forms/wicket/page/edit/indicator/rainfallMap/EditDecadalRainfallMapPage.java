package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfallMap;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap.ListDecadalRainfallMapPage;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.service.indicator.rainfallMap.DecadalRainfallMapService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath("/rainfall-map")
public class EditDecadalRainfallMapPage extends AbstractEditPage<DecadalRainfallMap> {
    private static final long serialVersionUID = -4145077002056116408L;

    @SpringBean
    private DecadalRainfallMapService decadalRainfallMapService;

    public EditDecadalRainfallMapPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = decadalRainfallMapService;
        setListPage(ListDecadalRainfallMapPage.class);
    }
}
