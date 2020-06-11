package org.devgateway.toolkit.forms.wicket.page.edit.reference;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainSeasonStartReferencePage;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/rain-season-start-reference")
public class EditRainSeasonStartReferencePage extends AbstractEditPage<RainSeasonStartReference> {
    private static final long serialVersionUID = 7537691894474881083L;

    @SpringBean
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    public EditRainSeasonStartReferencePage(PageParameters parameters) {
        super(parameters);

        this.jpaService = rainSeasonStartReferenceService;
        setListPage(ListRainSeasonStartReferencePage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RainSeasonStartTableViewPanel rainSeasonStartTableViewPanel = new RainSeasonStartTableViewPanel(
                "postReferences", editForm.getModel());
        editForm.add(rainSeasonStartTableViewPanel);

        deleteButton.setVisible(false);

        rainSeasonStartReferenceService.initialize(editForm.getModelObject());
    }
}
