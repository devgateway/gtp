package org.devgateway.toolkit.forms.wicket.page;

import static java.util.stream.Collectors.toList;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.checkbox.bootstraptoggle.BootstrapToggle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapCancelButton;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapSubmitButton;
import org.devgateway.toolkit.persistence.dao.Feature;
import org.devgateway.toolkit.persistence.service.FeatureService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@MountPath("/feature-manager")
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
public class FeatureManagerPage extends BasePage {

    @SpringBean
    private FeatureService featureService;

    public FeatureManagerPage(PageParameters parameters) {
        super(parameters);

        List<Feature> features = featureService.findAll().stream().sorted().collect(toList());

        Form<List<Feature>> form = new Form<>("form", Model.ofList(features));
        add(form);

        form.add(new BootstrapSubmitButton("save", new StringResourceModel("save")) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                form.getModelObject().forEach(featureService::save);
                setResponsePage(Homepage.class);
            }
        });

        form.add(new BootstrapCancelButton("cancel", new StringResourceModel("cancel")) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                setResponsePage(Homepage.class);
            }
        });

        WebMarkupContainer table = new WebMarkupContainer("table");
        table.setOutputMarkupId(true);
        form.add(table);

        table.add(new ListView<Feature>("features", features) {

            @Override
            protected void populateItem(ListItem<Feature> item) {
                item.add(new AttributeAppender("class", item.getModel().map(f -> "depth-" + f.getDepth())));
                item.add(new Label("label", item.getModel().map(Feature::getLabel)));

                item.add(new BootstrapToggle("enabled",
                        LambdaModel.of(item.getModel(),
                                Feature::isEnabledInHierarchy,
                                Feature::setEnabled)) {

                    @Override
                    protected CheckBox newCheckBox(String id, IModel<Boolean> model) {
                        CheckBox checkBox = super.newCheckBox(id, model);
                        checkBox.add(new AjaxFormComponentUpdatingBehavior("change") {
                            @Override
                            protected void onUpdate(AjaxRequestTarget target) {
                                target.add(table);
                            }
                        });
                        return checkBox;
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(item.getModelObject().getParent() == null
                                || item.getModelObject().getParent().isEnabledInHierarchy());
                    }

                    @Override
                    protected IModel<String> getOffLabel() {
                        return new StringResourceModel("disabled", this);
                    }

                    @Override
                    protected IModel<String> getOnLabel() {
                        return new StringResourceModel("enabled", this);
                    }
                });
            }
        }.setReuseItems(true));
    }
}
