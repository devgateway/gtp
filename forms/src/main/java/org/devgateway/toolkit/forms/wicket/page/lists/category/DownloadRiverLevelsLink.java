package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;

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
import org.devgateway.toolkit.forms.wicket.components.form.AJAXDownload;
import org.devgateway.toolkit.forms.wicket.page.edit.category.RiverLevelWriter;
import org.devgateway.toolkit.persistence.dao.RiverLevelReference;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.springframework.http.ContentDisposition;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadRiverLevelsLink extends BootstrapAjaxLink<RiverStationYearlyLevelsReference> {

    private final AJAXDownload download;

    public DownloadRiverLevelsLink(String id, IModel<RiverStationYearlyLevelsReference> model) {
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
                            RiverStationYearlyLevelsReference entity = model.getObject();

                            String fileName = String.format("%s - %s.xlsx", entity.getStation().getName(),
                                    entity.getYear());

                            response.setContentType(
                                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            response.setHeader("Content-Disposition", ContentDisposition.builder("attachment")
                                    .filename(fileName)
                                    .build()
                                    .toString());

                            List<RiverLevelReference> levels = empty ? getLevelForTemplate(entity) : entity.getLevels();

                            RiverLevelWriter writer = new RiverLevelWriter();
                            writer.write(entity.getYear(), levels, response.getOutputStream());
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

    private List<RiverLevelReference> getLevelForTemplate(RiverStationYearlyLevelsReference entity) {
        return entity.getYear().getMonthDays().stream()
                .map(RiverLevelReference::new)
                .collect(toList());
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        download.initiate(target);
    }
}
