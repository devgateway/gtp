package org.devgateway.toolkit.forms.wicket.page.edit.organization;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.TableViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata_;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class OrganizationIndicatorsPanel
        extends TableViewSectionPanel<IndicatorMetadata, Organization> {

    @SpringBean
    protected IndicatorMetadataService indicatorMetadataService;

    public OrganizationIndicatorsPanel(String id, IModel<Organization> parentModel) {
        super(id, parentModel);
        this.isReadOnly = true;

        this.dataProvider =  new SortableJpaServiceDataProvider<IndicatorMetadata>(indicatorMetadataService);
        ((SortableJpaServiceDataProvider) dataProvider).setFilterState(newFilterState());
        columns.add(new PropertyColumn<IndicatorMetadata, String>(new StringResourceModel("name", this, null), "name"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.addButton.setVisible(false);
    }

    @Override
    protected JpaFilterState<IndicatorMetadata> newFilterState() {
        List<Long> ids = this.parentModel.getObject().getIndicators()
                .stream()
                .map(IndicatorMetadata::getId)
                .collect(Collectors.toList());
        return new JpaFilterState<IndicatorMetadata>() {
            @Override
            public Specification<IndicatorMetadata> getSpecification() {
                return (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (ids.isEmpty()) {
                        predicates.add(cb.equal(root.get(IndicatorMetadata_.id), -1L));
                    } else {
                        predicates.add(root.get(IndicatorMetadata_.id).in(ids));
                    }
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                };
            }
        };
    }

    @Override
    protected IndicatorMetadata createNewChild() {
        return null;
    }

    @Override
    protected void deleteChild(IModel<IndicatorMetadata> child, AjaxRequestTarget target) {

    }

}
