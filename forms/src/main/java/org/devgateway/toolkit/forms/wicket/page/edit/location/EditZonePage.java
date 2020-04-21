package org.devgateway.toolkit.forms.wicket.page.edit.location;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.location.ZoneService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/zone")
public class EditZonePage extends EditAbstractLocation<Zone> {
    private static final long serialVersionUID = 3649329603143976135L;

    @SpringBean
    private ZoneService zoneService;

    public EditZonePage(PageParameters parameters) {
        super(parameters);

        this.jpaService = zoneService;
    }
}
