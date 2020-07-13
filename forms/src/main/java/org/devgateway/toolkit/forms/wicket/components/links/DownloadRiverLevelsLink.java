package org.devgateway.toolkit.forms.wicket.components.links;

import static java.util.stream.Collectors.toCollection;

import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.service.indicator.river.RiverLevelWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadRiverLevelsLink
        <T extends AbstractImportableEntity & IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends AbstractGeneratedExcelDownloadLink<T> {

    private final SerializableSupplier<T> entityCreator;
    private final SerializableBiFunction<MonthDay, BigDecimal, L> levelCreator;

    public DownloadRiverLevelsLink(String id, IModel<T> model,
            SerializableSupplier<T> entityCreator,
            SerializableBiFunction<MonthDay, BigDecimal, L> levelCreator) {
        this(id, model, entityCreator, levelCreator, null);
    }

    public DownloadRiverLevelsLink(String id, IModel<T> model,
            SerializableSupplier<T> entityCreator,
            SerializableBiFunction<MonthDay, BigDecimal, L> levelCreator, Boolean template) {
        super(id, model, template);
        this.entityCreator = entityCreator;
        this.levelCreator = levelCreator;
    }

    @Override
    protected String getFileName() {
        T entity = getModelObject();
        return String.format("%s - %s.xlsx", entity.getStation().getName(), entity.getYear());
    }

    @Override
    protected T getTemplateObject() {
        T entity = entityCreator.get();
        entity.setYear(getModelObject().getYear());
        getLevelForTemplate(entity, levelCreator).forEach(l -> entity.addLevel(l));
        return entity;
    }

    @Override
    protected void generate(T entity, OutputStream outputStream) throws IOException {
        RiverLevelWriter writer = new RiverLevelWriter();
        writer.write(entity.getYear(), entity.getLevels(), outputStream, levelCreator);
    }

    private SortedSet<L> getLevelForTemplate(T entity, BiFunction<MonthDay, BigDecimal, L> creator) {
        return entity.getYear().getMonthDays().stream()
                .map(md -> creator.apply(md, null))
                .collect(toCollection(TreeSet::new));
    }
}
