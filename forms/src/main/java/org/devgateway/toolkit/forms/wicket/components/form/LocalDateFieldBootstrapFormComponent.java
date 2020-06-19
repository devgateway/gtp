package org.devgateway.toolkit.forms.wicket.components.form;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.AbstractDateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.LocalDateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.LocalDateTextFieldConfig;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

import java.time.LocalDate;

/**
 * @author Nadejda Mandrescu
 */
public class LocalDateFieldBootstrapFormComponent extends AbstractDateFieldBootstrapFormComponent<LocalDate> {
    private static final long serialVersionUID = 7896197163912288823L;

    public LocalDateFieldBootstrapFormComponent(String id, IModel<String> labelModel, IModel<LocalDate> model) {
        super(id, labelModel, model);
    }

    public LocalDateFieldBootstrapFormComponent(String id) {
        super(id);
    }

    public LocalDateFieldBootstrapFormComponent(String id, IModel<LocalDate> model) {
        super(id, model);
    }

    @Override
    protected LocalDateTextFieldConfig newDateConfig() {
        return new LocalDateTextFieldConfig();
    }

    @Override
    protected LocalDateTextField newDateTextField(String id, AbstractDateTextFieldConfig config) {
        return new LocalDateTextField(id, initFieldModel(), (LocalDateTextFieldConfig) config) {

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("autocomplete", "off");
            }
        };
    }
}
