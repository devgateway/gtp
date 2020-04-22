package org.devgateway.toolkit.forms.wicket.page.edit.location;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.location.RegionService;
import org.devgateway.toolkit.persistence.service.location.ZoneService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/region")
public class EditRegionPage extends EditAbstractLocation<Region> {
    private static final long serialVersionUID = -1803608505356123680L;

    @SpringBean
    private ZoneService zoneService;

    @SpringBean
    private RegionService regionService;

    public EditRegionPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = regionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final Select2ChoiceBootstrapFormComponent<Zone> zone = new Select2ChoiceBootstrapFormComponent<>("zone",
                new GenericPersistableJpaTextChoiceProvider<Zone>(zoneService));
        zone.required();
        zone.setEnabled(false);
        editForm.add(zone);
    }
}
