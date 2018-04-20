package org.devgateway.toolkit.forms.wicket.components.form;

import com.google.common.io.BaseEncoding;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditorCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditorFormDataReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditorJavaScriptReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditorOverlayCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteStorage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteStoredImageResourceReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.references.SpinJsReference;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A summer note editor
 *
 * @author Tobias Soloschenko
 */
public class ToolkitSummernoteEditor extends FormComponent<String> {

    private static final long serialVersionUID = 1L;

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("(\\r\\n|\\n|\\r)");

    private final SummernoteConfig config;

    private final SummernoteEditorImageAjaxEventBehavior summernoteEditorImageAjaxEventBehavior;

    public ToolkitSummernoteEditor(final String id) {
        this(id, null, new SummernoteConfig());
    }

    public ToolkitSummernoteEditor(final String id, final IModel<String> model) {
        this(id, model, new SummernoteConfig());
    }

    public ToolkitSummernoteEditor(final String id, final IModel<String> model, final SummernoteConfig config) {
        super(id, model);

        this.setOutputMarkupId(true);
        this.config = Args.notNull(config, "config");
        if (config.isAirMode()) {
            setEscapeModelStrings(false);
        }
        summernoteEditorImageAjaxEventBehavior = new SummernoteEditorImageAjaxEventBehavior();
        add(summernoteEditorImageAjaxEventBehavior);
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);

        if (config.isAirMode()) {
            tag.setName("div");
        }
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);

        if (config.isAirMode()) {
            replaceComponentTagBody(markupStream, openTag, getModelObject());
        }
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(SummernoteEditorCssReference.instance()));
        response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));
        response.render(CssHeaderItem.forReference(SummernoteEditorOverlayCssReference.instance()));
        response.render(JavaScriptHeaderItem.forReference(SummernoteEditorJavaScriptReference.instance()));
        response.render(JavaScriptHeaderItem.forReference(SummernoteEditorFormDataReference.instance()));
        response.render(JavaScriptHeaderItem.forReference(SpinJsReference.INSTANCE));
        PackageTextTemplate summernoteTemplate = null;
        try {
            summernoteTemplate = new PackageTextTemplate(
                    de.agilecoders.wicket.extensions.markup.html.bootstrap.editor.SummernoteEditor.class,
                    "js/summernote_init.js"
            );
            config.withImageUploadCallbackUrl(summernoteEditorImageAjaxEventBehavior.getCallbackUrl().toString());
            config.put(SummernoteConfig.Id, getMarkupId());

            // Remove picture button if no storage id is provided
            if (config.getStorageId() == null) {
                config.getButtons("Insert").remove("picture");
            }
            String jsonConfig = config.toJsonString();
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("summernoteconfig", jsonConfig);
            String summernoteTemplateJavaScript = summernoteTemplate.asString(variables);
            response.render(OnDomReadyHeaderItem.forScript(summernoteTemplateJavaScript));
        } finally {
            IOUtils.closeQuietly(summernoteTemplate);
        }

        String modelObject = getModelObject();
        if (!config.isAirMode() && !Strings.isEmpty(modelObject)) {
            modelObject = NEW_LINE_PATTERN.matcher(modelObject).replaceAll("<br/>");
            CharSequence safeModelObject = JavaScriptUtils.escapeQuotes(modelObject);
            response.render(OnDomReadyHeaderItem.forScript(String.format("$('#%s').summernote('code', '%s')",
                    getMarkupId(), safeModelObject
            )));
        }
    }

    /**
     * If an image is going to be inserted into the editor this callback is
     * going to be invoked for each image
     *
     * @param target       the target to update components
     * @param fileItemsMap a map with the image name and the list of file items (the image)
     */
    protected void onImageUpload(final AjaxRequestTarget target, final Map<String, FileItem> fileItemsMap) {
        // NOOP
    }

    /**
     * If an error occurred while inserting an image into the editor this
     * callback is going to be invoked for each image which can be uploaded
     *
     * @param target the target to update components (to give a hint that the
     *               upload failed)
     * @param fux    the exception
     */
    protected void onImageError(final AjaxRequestTarget target, final FileUploadException fux) {
        // NOOP
    }

    private class SummernoteEditorImageAjaxEventBehavior extends AbstractDefaultAjaxBehavior {

        private static final long serialVersionUID = 1L;

        @Override
        protected void respond(final AjaxRequestTarget target) {
            try {
                ServletWebRequest webRequest = (ServletWebRequest) getRequest();
                MultipartServletWebRequest multiPartRequest = webRequest.newMultipartWebRequest(
                        Bytes.megabytes(config.getMaxFileSize()), "ignored");
                multiPartRequest.parseFileParts();
                Map<String, FileItem> fileItemsMap = storeFile(target, multiPartRequest);
                onImageUpload(target, fileItemsMap);
            } catch (FileUploadException fux) {
                onImageError(target, fux);
            }
        }

        /**
         * Stores the submitted files
         *
         * @param target           the target to apply the url to the response
         * @param multiPartRequest the multi part request to get the files from
         * @throws IOException if an exception occurred while reading / writing any file
         */
        private Map<String, FileItem> storeFile(final AjaxRequestTarget target, final MultipartServletWebRequest
                multiPartRequest) {
            Map<String, FileItem> fileItemsMap = new LinkedHashMap<String, FileItem>();
            Map<String, List<FileItem>> fileMap = multiPartRequest.getFiles();
            Iterator<List<FileItem>> fileItemListIterator = fileMap.values().iterator();
            while (fileItemListIterator.hasNext()) {
                Iterator<FileItem> fileItemIterator = fileItemListIterator.next().iterator();
                while (fileItemIterator.hasNext()) {
                    FileItem fileItem = fileItemIterator.next();
                    String imageName = config.getImageNamePrefix() + fileItem.getName();
                    try {
                        SummernoteStorage storage = SummernoteConfig.getStorage(config.getStorageId());
                        storage.writeContent(imageName, fileItem.getInputStream());
                        WebResponse response = (WebResponse) target.getHeaderResponse().getResponse();
                        response.setHeader("imageUrl", SummernoteStoredImageResourceReference.SUMMERNOTE_MOUNT_PATH
                                + "?image="
                                + BaseEncoding.base64().encode(imageName.getBytes()));
                        fileItemsMap.put(imageName, fileItem);
                    } catch (IOException e) {
                        throw new WicketRuntimeException("Error while writing image: " + imageName, e);
                    }
                }
            }
            return fileItemsMap;
        }

        @Override
        protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {
            super.updateAjaxAttributes(attributes);
            attributes.setMultipart(true);
        }
    }

}
