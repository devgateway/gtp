package org.devgateway.toolkit.forms.wicket.components.links;

import java.io.IOException;
import java.io.OutputStream;

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
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.springframework.http.ContentDisposition;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractGeneratedExcelDownloadLink<T extends AbstractImportableEntity>
        extends BootstrapAjaxLink<T> {

    private final AJAXDownload download;

    private final Boolean template;

    public AbstractGeneratedExcelDownloadLink(String id, IModel<T> model, Boolean template) {
        super(id, model, Buttons.Type.Info);

        this.template = template;

        setLabel(new StringResourceModel(isTemplate() ? "downloadTemplate" : "download", this));
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
                            response.setContentType(
                                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            response.setHeader("Content-Disposition", ContentDisposition.builder("attachment")
                                    .filename(getFileName())
                                    .build()
                                    .toString());

                            T object = isTemplate() ? getTemplateObject() : getModelObject();

                            generate(object, response.getOutputStream());
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

    private boolean isTemplate() {
        return template == null ? getModelObject().isEmpty() : template;
    }

    protected abstract String getFileName();

    protected abstract T getTemplateObject();

    protected abstract void generate(T object, OutputStream outputStream) throws IOException;

    @Override
    public void onClick(AjaxRequestTarget target) {
        download.initiate(target);
    }
}
