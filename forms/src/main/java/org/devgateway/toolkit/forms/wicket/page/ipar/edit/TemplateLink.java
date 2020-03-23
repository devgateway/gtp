package org.devgateway.toolkit.forms.wicket.page.ipar.edit;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import java.io.IOException;
import java.io.OutputStream;

public class TemplateLink extends Link<String> {

    private static final long serialVersionUID = 1L;

    private String templateFile;

    public TemplateLink(String id, String templateFile) {
        super(id);
        this.templateFile = templateFile;

    }

    @Override
    public void onClick() {
        AbstractResourceStreamWriter rstream = new AbstractResourceStreamWriter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void write(final OutputStream output) throws IOException {
                output.write(IOUtils.toByteArray(getClass().getResourceAsStream(templateFile)));
            }

            @Override
            public String getContentType() {
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
        };

        ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(rstream, templateFile);
        handler.setContentDisposition(ContentDisposition.ATTACHMENT);
        getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }

    @Override
    public void detachModels() {
        super.detachModels();
    }
}
