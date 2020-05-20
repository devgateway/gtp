package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import java.math.BigDecimal;
import java.time.MonthDay;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.page.lists.category.DownloadRiverLevelsLink;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationYearlyLevelsActionPanel
        <T extends GenericPersistable & IRiverStationYearlyLevels<L>, L extends IRiverLevel> extends Panel {

    public RiverStationYearlyLevelsActionPanel(String id, IModel<T> model,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator, Class<? extends Page> editPageClass) {

        super(id, model);

        T entity = model.getObject();

        final PageParameters pageParameters = new PageParameters();
        pageParameters.set(WebConstants.PARAM_ID, entity.getId());

        BootstrapBookmarkablePageLink<?> editPageLink =
                new BootstrapBookmarkablePageLink<>("edit", editPageClass, pageParameters, Buttons.Type.Info);

        String editResourceKey = entity.getLevels().isEmpty() ? "import" : "reimport";
        editPageLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Small)
                .setLabel(new StringResourceModel(editResourceKey, this, null));
        add(editPageLink);

        DownloadRiverLevelsLink<T, L> downloadButton =
                new DownloadRiverLevelsLink<>("download", model, creator);
        downloadButton.setSize(Buttons.Size.Small);
        downloadButton.setVisibilityAllowed(!entity.getLevels().isEmpty());
        add(downloadButton);

        add(new WebMarkupContainer("printButton").setVisibilityAllowed(false));
    }
}
