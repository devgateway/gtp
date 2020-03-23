package org.devgateway.toolkit.forms.wicket.page.edit.panel;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.ListViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.dao.ipar.GisSettingsDescription;
import org.devgateway.toolkit.persistence.dao.ipar.categories.GisIndicator;
import org.devgateway.toolkit.persistence.service.ipar.category.GisIndicatorService;


/**
 * @author dbianco
 */
public class GisSettingsDescPanel extends ListViewSectionPanel<GisSettingsDescription, GisSettings> {

    @SpringBean
    private GisIndicatorService gisIndicatorService;

    public GisSettingsDescPanel(String id) {
        super(id);
    }

    @Override
    public GisSettingsDescription createNewChild(IModel<GisSettings> parentModel) {
        final GisSettingsDescription child = new GisSettingsDescription();
        child.setGisSettings(parentModel.getObject());
        return child;
    }

    @Override
    public void populateCompoundListItem(ListItem<GisSettingsDescription> item) {

        Select2ChoiceBootstrapFormComponent<GisIndicator> gisIndicator =
                new Select2ChoiceBootstrapFormComponent<GisIndicator>("gisIndicator",
                        new GenericPersistableJpaTextChoiceProvider<GisIndicator>(gisIndicatorService));
        ComponentUtil.addRequiredFlagBootstrapFormComponent(true, item, gisIndicator);

        final TextFieldBootstrapFormComponent<String> descriptionFr = ComponentUtil.addTextField(item, "descriptionFr");
        descriptionFr.required();

        final TextFieldBootstrapFormComponent<String> description = ComponentUtil.addTextField(item, "description");
    }
}
