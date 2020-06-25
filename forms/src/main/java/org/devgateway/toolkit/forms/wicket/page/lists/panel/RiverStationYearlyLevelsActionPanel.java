package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import java.math.BigDecimal;
import java.time.MonthDay;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadRiverLevelsLink;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationYearlyLevelsActionPanel
        <T extends AbstractImportableEntity & IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends AbstractExcelListActionPanel<T> {

    private final SerializableSupplier<T> entityCreator;
    private final SerializableBiFunction<MonthDay, BigDecimal, L> creator;

    public RiverStationYearlyLevelsActionPanel(String id, IModel<T> model,
            SerializableSupplier<T> entityCreator,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator, Class<? extends Page> editPageClass) {
        super(id, model, editPageClass);

        this.entityCreator = entityCreator;
        this.creator = creator;
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        return new DownloadRiverLevelsLink<>(id, getModel(), entityCreator, creator);
    }
}
