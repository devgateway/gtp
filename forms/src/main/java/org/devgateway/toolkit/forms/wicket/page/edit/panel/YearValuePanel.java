package org.devgateway.toolkit.forms.wicket.page.edit.panel;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.ListViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.persistence.dao.ipar.NationalIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.NationalIndicatorYearValue;

/**
 * @author dbianco
 */
public class YearValuePanel extends ListViewSectionPanel<NationalIndicatorYearValue, NationalIndicator> {

    public YearValuePanel(final String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public NationalIndicatorYearValue createNewChild(final IModel<NationalIndicator> parentModel) {
        final NationalIndicatorYearValue child = new NationalIndicatorYearValue();
        child.setNationalIndicator(parentModel.getObject());

        return child;
    }

    @Override
    public void populateCompoundListItem(final ListItem<NationalIndicatorYearValue> item) {
        final TextFieldBootstrapFormComponent<String> year = ComponentUtil.addTextField(item, "year");
        year.required();

        final TextFieldBootstrapFormComponent<Double> value = ComponentUtil.addDoubleField(item, "value");
        value.required();
    }
}
