package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.Collection;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadRiverLevelsLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.providers.HydrologicalYearRangeChoiceProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.util.JPAUtil;

/**
 * @author Octavian Ciubotaru
 */
public class AbstractEditRiverStationYearlyLevelsPage
        <T extends GenericPersistable & IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends AbstractExcelImportPage<T> {

    private final SerializableBiFunction<MonthDay, BigDecimal, L> creator;

    protected Select2ChoiceBootstrapFormComponent<HydrologicalYear> year;

    public AbstractEditRiverStationYearlyLevelsPage(PageParameters parameters,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator) {
        super(parameters);

        this.creator = creator;
    }

    protected void onInitialize(int minYear) {
        TextFieldBootstrapFormComponent<String> river = new TextFieldBootstrapFormComponent<>("station.river.name");
        river.getField().setEnabled(false);
        editForm.add(river);

        TextFieldBootstrapFormComponent<String> station = new TextFieldBootstrapFormComponent<>("station.name");
        station.getField().setEnabled(false);
        editForm.add(station);

        year = new Select2ChoiceBootstrapFormComponent<>("year",
                new HydrologicalYearRangeChoiceProvider(minYear, HydrologicalYear.now().getYear()));
        editForm.add(year);

        deleteButton.setVisible(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id) {
        return new DownloadRiverLevelsLink<>("download", editForm.getModel(), creator);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        RiverLevelReader reader = new RiverLevelReader();

        Collection<L> levels = reader.read(inputStream, creator);

        T entity = editForm.getModelObject();

        JPAUtil.mergeSortedSet(levels, entity.getLevels(),
                l -> entity.addLevel(l),
                (oldItem, newItem) -> oldItem.setLevel(newItem.getLevel()));
    }
}
