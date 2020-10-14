package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfallMap;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.MaxFileSizeValidator;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap.ListDecadalRainfallMapPage;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayer;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;
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

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        editForm.add(new Label("layersInfo", new StringResourceModel("layersInfo")).setEscapeModelStrings(false));

        DecadalRainfallMap decadalMap = editForm.getModelObject();
        for (RainfallMapLayerType type : RainfallMapLayerType.values()) {
            addLayer(decadalMap.computeIfAbsent(type));
        }

        deleteButton.setVisible(false);
    }

    private void addLayer(RainfallMapLayer layer) {
        FileInputBootstrapFormComponent file = new FileInputBootstrapFormComponent(
                layer.getType().name(), new PropertyModel<>(layer, "file"));
        file.maxFiles(1);
        file.allowedFileExtensions("json");
        file.getField().add(new MaxFileSizeValidator(RainfallMapLayer.MAX_FILE_SIZE, file.getField()));
        file.required();
        editForm.add(file);
    }
}
