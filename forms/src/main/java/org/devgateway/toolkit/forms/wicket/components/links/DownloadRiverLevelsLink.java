package org.devgateway.toolkit.forms.wicket.components.links;

import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;

import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.devgateway.toolkit.forms.wicket.page.edit.category.RiverLevelWriter;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadRiverLevelsLink<T extends IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends AbstractGeneratedExcelDownloadLink<T> {

    private final SerializableBiFunction<MonthDay, BigDecimal, L> creator;

    public DownloadRiverLevelsLink(String id, IModel<T> model,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator) {
        super(id, model);
        this.creator = creator;
    }

    @Override
    protected String getFileName() {
        T entity = getModelObject();
        return String.format("%s - %s.xlsx", entity.getStation().getName(), entity.getYear());
    }

    @Override
    protected boolean isEmpty() {
        return getModelObject().getLevels().isEmpty();
    }

    @Override
    protected void generate(OutputStream outputStream) throws IOException {
        T entity = getModelObject();

        SortedSet<L> levels = isEmpty()
                ? getLevelForTemplate(entity, creator)
                : entity.getLevels();

        RiverLevelWriter writer = new RiverLevelWriter();
        writer.write(entity.getYear(), levels, outputStream, creator);
    }

    private SortedSet<L> getLevelForTemplate(T entity, BiFunction<MonthDay, BigDecimal, L> creator) {
        return entity.getYear().getMonthDays().stream()
                .map(md -> creator.apply(md, null))
                .collect(toCollection(TreeSet::new));
    }
}
