package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Objects;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.UniquePropertyValidator;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListRiversPage;
import org.devgateway.toolkit.persistence.dao.categories.River;
import org.devgateway.toolkit.persistence.service.category.RiverService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR)
@MountPath(value = "/river")
public class EditRiverPage extends AbstractEditPage<River> {
    private static final long serialVersionUID = -5926912837806404163L;

    @SpringBean
    private RiverService riverService;

    public EditRiverPage(PageParameters parameters) {
        super(parameters);

        jpaService = riverService;

        setListPage(ListRiversPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        name.getField().add(new UniquePropertyValidator<>(riverService,
                Objects.defaultIfNull(entityId, -1L), "name", this));
        editForm.add(name);

        deleteButton.setVisible(false);
    }
}
