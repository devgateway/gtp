package org.devgateway.toolkit.forms.wicket.components.links;

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
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayer;
import org.springframework.http.ContentDisposition;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadDecadalRainfallMapLink extends BootstrapAjaxLink<DecadalRainfallMap> {
    private static final long serialVersionUID = -1769832809253992274L;

    private final AJAXDownload download;

    public DownloadDecadalRainfallMapLink(String id, IModel<DecadalRainfallMap> model) {
        super(id, model, Buttons.Type.Info);

        setLabel(new StringResourceModel("download", this));
        setIconType(FontAwesomeIconType.download);

        download = new AJAXDownload() {
            private static final long serialVersionUID = 5337962922344979155L;

            @Override
            protected IRequestHandler getHandler() {
                return new IRequestHandler() {
                    @Override
                    public void respond(final IRequestCycle requestCycle) {
                        final HttpServletResponse response =
                                (HttpServletResponse) requestCycle.getResponse().getContainerResponse();

                        try {
                            response.setContentType("application/zip");
                            response.setHeader("Content-Disposition", ContentDisposition.builder("attachment")
                                    .filename(String.format("%s.zip", getFilenamePrefix()))
                                    .build()
                                    .toString());
                            response.flushBuffer();

                            final ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(
                                    response.getOutputStream()));
                            zout.setMethod(ZipOutputStream.DEFLATED);
                            zout.setLevel(Deflater.NO_COMPRESSION);

                            DecadalRainfallMap object = getModelObject();
                            for (RainfallMapLayer layer : object.getLayers()) {
                                if (!layer.isEmpty()) {
                                    generate(layer, zout, response);
                                }
                            }

                            zout.close();
                            response.flushBuffer();
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

    protected String getFilenamePrefix() {
        return getString("filePrefix", getModel());
    }

    protected void generate(RainfallMapLayer layer, ZipOutputStream zout, HttpServletResponse response)
            throws IOException {
        final ZipEntry ze = new ZipEntry(getFilenamePrefix() + "-" + getString(layer.getType().name()) + ".json");
        byte[] bytes = layer.getFileSingle().getContent().getBytes();

        zout.putNextEntry(ze);
        zout.write(bytes, 0, bytes.length);
        zout.closeEntry();
        response.flushBuffer();
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        download.initiate(target);
    }
}
