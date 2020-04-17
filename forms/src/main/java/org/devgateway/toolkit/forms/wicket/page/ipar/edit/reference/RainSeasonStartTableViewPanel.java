package org.devgateway.toolkit.forms.wicket.page.ipar.edit.reference;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.TableViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonPluviometricPostReferenceStartService;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonStartTableViewPanel
        extends TableViewSectionPanel<RainSeasonPluviometricPostReferenceStart, RainSeasonStartReference> {
    private static final long serialVersionUID = -4392759969371109961L;

    @SpringBean
    private RainSeasonPluviometricPostReferenceStartService rainSeasonPostReferenceService;

    @SpringBean
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    public RainSeasonStartTableViewPanel(String id, IModel<RainSeasonStartReference> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("title", parentModel);

        init();

        this.dataProvider = new SortableJpaServiceDataProvider<>(rainSeasonPostReferenceService);
        ((SortableJpaServiceDataProvider) dataProvider).setFilterState(newFilterState());
        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "pluviometricPost.department.name",
                "pluviometricPost.department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("pluviometricPost"), "pluviometricPost.label",
                "pluviometricPost.label"));
    }

    private void init() {
        rainSeasonStartReferenceService.initialize(parentModel.getObject());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addButton.setVisible(false);
    }

    @Override
    protected RainSeasonPluviometricPostReferenceStart createNewChild() {
        return null;
    }

    @Override
    protected void deleteChild(IModel<RainSeasonPluviometricPostReferenceStart> child, AjaxRequestTarget target) {

    }
}
