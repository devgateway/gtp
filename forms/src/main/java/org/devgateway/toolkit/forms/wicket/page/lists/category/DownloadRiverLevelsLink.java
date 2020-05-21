package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletResponse;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.devgateway.toolkit.forms.wicket.components.form.AJAXDownload;
import org.devgateway.toolkit.forms.wicket.page.edit.category.RiverLevelWriter;
import org.devgateway.toolkit.persistence.dao.IRiverLevel;
import org.devgateway.toolkit.persistence.dao.IRiverStationYearlyLevels;
import org.springframework.http.ContentDisposition;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadRiverLevelsLink<T extends IRiverStationYearlyLevels<L>, L extends IRiverLevel>
        extends BootstrapAjaxLink<T> {

    private final AJAXDownload download;

    public DownloadRiverLevelsLink(String id, IModel<T> model,
            SerializableBiFunction<MonthDay, BigDecimal, L> creator) {

        super(id, model, Buttons.Type.Info);

        boolean empty = model.getObject().getLevels().isEmpty();

        setLabel(new StringResourceModel(empty ? "downloadTemplate" : "download", this));
        setIconType(FontAwesomeIconType.download);

        download = new AJAXDownload() {

            @Override
            protected IRequestHandler getHandler() {
                return new IRequestHandler() {
                    @Override
                    public void respond(final IRequestCycle requestCycle) {
                        final HttpServletResponse response =
                                (HttpServletResponse) requestCycle.getResponse().getContainerResponse();

                        try {
                            T entity = model.getObject();

                            String fileName = String.format("%s - %s.xlsx", entity.getStation().getName(),
                                    entity.getYear());

                            response.setContentType(
                                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            response.setHeader("Content-Disposition", ContentDisposition.builder("attachment")
                                    .filename(fileName)
                                    .build()
                                    .toString());

                            SortedSet<L> levels = empty
                                    ? getLevelForTemplate(entity, creator)
                                    : entity.getLevels();

                            RiverLevelWriter writer = new RiverLevelWriter();
                            writer.write(entity.getYear(), levels, response.getOutputStream(), creator);
                        } catch (IOException e) {
                            throw new WicketRuntimeException("Download error", e);
                        }

                        RequestCycle.get().scheduleRequestHandlerAfterCurrent(null);
                    }

                    @Override
                    public void detach(final IRequestCycle requestCycle) {
                        // do nothing;
                    }
                };
            }
        };

        add(download);
    }

    private SortedSet<L> getLevelForTemplate(T entity, BiFunction<MonthDay, BigDecimal, L> creator) {
        return entity.getYear().getMonthDays().stream()
                .map(md -> creator.apply(md, null))
                .collect(toCollection(TreeSet::new));
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        download.initiate(target);
    }
}
