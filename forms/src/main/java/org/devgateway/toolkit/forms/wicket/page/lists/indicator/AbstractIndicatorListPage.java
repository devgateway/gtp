package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.page.Homepage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;

import java.io.Serializable;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractIndicatorListPage<T extends AbstractStatusAuditableEntity & Serializable>
        extends AbstractListPage<T> {
    private static final long serialVersionUID = 6197757854327171468L;

    @SpringBean
    protected IndicatorMetadataService indicatorMetadataService;

    protected IndicatorType indicatorType;

    public AbstractIndicatorListPage(PageParameters parameters, IndicatorType indicatorType) {
        super(parameters, false);
        this.indicatorType = indicatorType;
    }

    @Override
    protected void onInitialize() {
        columns.add(new AbstractColumn<T, String>(new StringResourceModel("formStatus")) {
            private static final long serialVersionUID = -5425273193416180043L;
            @Override
            public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
                String statusName = rowModel.getObject().getFormStatus().name();
                cellItem.add(new Label(componentId, new StringResourceModel(statusName)));
            }
        });
        super.onInitialize();

        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findOneByType(indicatorType);
        if (!SecurityUtil.canCurrentUserAccess(indicatorMetadata)) {
            setResponsePage(Homepage.class);
        }
    }
}
