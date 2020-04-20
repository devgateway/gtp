package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainseason;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.AbstractIndicatorEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason.ListRainSeasonPage;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/rain-season")
public class EditRainSeasonPage extends AbstractIndicatorEditPage<PluviometricPostRainSeason> {
    private static final long serialVersionUID = -7281354183801334756L;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    public EditRainSeasonPage(PageParameters parameters) {
        super(parameters, IndicatorType.RAINFALL_SEASON);

        this.jpaService = pluviometricPostRainSeasonService;
        this.listPageClass = ListRainSeasonPage.class;
    }
}
