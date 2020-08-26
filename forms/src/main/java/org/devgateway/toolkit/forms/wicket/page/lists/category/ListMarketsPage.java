package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static java.util.stream.Collectors.joining;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.DegreePropertyColumn;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditMarketPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/markets")
public class ListMarketsPage extends AbstractListPage<Market> {
    private static final long serialVersionUID = 9024914359658502410L;

    @SpringBean
    private MarketService marketService;

    public ListMarketsPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = marketService;
        this.editPageClass = EditMarketPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "department.name", "department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("name"), "name", "name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("type"), "type", "type"));

        StringResourceModel marketDays = new StringResourceModel("marketDays");
        columns.add(new PropertyColumn<Market, String>(marketDays, "marketDays", "marketDays") {
            private static final long serialVersionUID = -5169611111892698089L;

            @Override
            @SuppressWarnings("unchecked")
            public IModel<?> getDataModel(IModel<Market> rowModel) {
                return new MarketDaysModel((IModel<Integer>) super.getDataModel(rowModel),
                        new StringResourceModel("permanent", ListMarketsPage.this));
            }
        });

        columns.add(new DegreePropertyColumn<>(new StringResourceModel("latitude"), "latitude", "latitude"));
        columns.add(new DegreePropertyColumn<>(new StringResourceModel("longitude"), "longitude", "longitude"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        excelForm.setVisibilityAllowed(true);
    }

    private static class MarketDaysModel implements IModel<String> {
        private static final long serialVersionUID = -4722198040373926813L;

        private final IModel<Integer> targetModel;
        private final IModel<String> permanentLabelModel;

        MarketDaysModel(IModel<Integer> targetModel, IModel<String> permanentLabelModel) {
            this.targetModel = targetModel;
            this.permanentLabelModel = permanentLabelModel;
        }

        @Override
        public String getObject() {
            Integer flags = targetModel.getObject();
            if (flags.equals(MarketDaysUtil.ALL_DAYS)) {
                return permanentLabelModel.getObject();
            }
            List<DayOfWeek> days = MarketDaysUtil.unpack(flags);
            TextStyle textStyle = days.size() == 1 ? TextStyle.FULL_STANDALONE : TextStyle.NARROW;
            return days.stream()
                    .map(day -> day.getDisplayName(textStyle, Session.get().getLocale()))
                    .collect(joining(", "));
        }
    }
}
