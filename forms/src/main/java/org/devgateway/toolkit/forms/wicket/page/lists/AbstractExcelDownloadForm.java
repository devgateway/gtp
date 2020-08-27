package org.devgateway.toolkit.forms.wicket.page.lists;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.form.AJAXDownload;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.excel.service.ExcelGeneratorService;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A wrapper form that is used to fire the excel download action
 */
public abstract class AbstractExcelDownloadForm<T extends GenericPersistable & Serializable> extends Form<Void> {
    private static final long serialVersionUID = -5313470286932341397L;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractExcelDownloadForm.class);

    @SpringBean
    private ExcelGeneratorService excelGeneratorService;

    public AbstractExcelDownloadForm(final String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final AJAXDownload download = new AJAXDownload() {
            private static final long serialVersionUID = 970961723339623013L;

            @Override
            protected IRequestHandler getHandler() {
                return new IRequestHandler() {
                    @Override
                    public void respond(final IRequestCycle requestCycle) {
                        final HttpServletResponse response = (HttpServletResponse) requestCycle
                                .getResponse().getContainerResponse();

                        try {
                            final int batchSize = 10000;

                            final long count = excelGeneratorService.count(
                                    getJpaService(),
                                    getDataProvider().getFilterState().getSpecification());

                            // if we need to export just one file then we don't create an archive
                            if (count <= batchSize) {
                                // set a maximum download of objects per excel file
                                final PageRequest pageRequest = PageRequest.of(0, batchSize,
                                        Sort.Direction.ASC, "id");

                                final byte[] bytes = excelGeneratorService.getExcelDownload(
                                        getJpaService(),
                                        getDataProvider().getFilterState().getSpecification(),
                                        pageRequest);

                                response.setContentType(
                                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                                response.setHeader("Content-Disposition", "attachment; filename=excel-export.xlsx");
                                response.getOutputStream().write(bytes);
                            } else {
                                response.setContentType("application/zip");
                                response.setHeader("Content-Disposition", "attachment; filename=excel-export.zip");
                                response.flushBuffer();
                                final ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(
                                        response.getOutputStream()));
                                zout.setMethod(ZipOutputStream.DEFLATED);
                                zout.setLevel(Deflater.NO_COMPRESSION);
                                final int numberOfPages = (int) Math.ceil((double) count / batchSize);
                                for (int i = 0; i < numberOfPages; i++) {
                                    final PageRequest pageRequest = PageRequest.of(i, batchSize,
                                            Sort.Direction.ASC, "id");
                                    final byte[] bytes = excelGeneratorService.getExcelDownload(
                                            getJpaService(),
                                            getDataProvider().getFilterState().getSpecification(),
                                            pageRequest);
                                    final ZipEntry ze = new ZipEntry("excel-export-page " + (i + 1) + ".xlsx");
                                    zout.putNextEntry(ze);
                                    zout.write(bytes, 0, bytes.length);
                                    zout.closeEntry();
                                    response.flushBuffer();
                                }
                                zout.close();
                                response.flushBuffer();
                            }
                        } catch (IOException e) {
                            logger.error("Download error", e);
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

        final LaddaAjaxButton excelButton = new LaddaAjaxButton("excelButton",
                new StringResourceModel("excelButton"),
                Buttons.Type.Warning) {
            private static final long serialVersionUID = -1207967121514699929L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);

                // initiate the file download
                download.initiate(target);
            }
        };
        excelButton.setIconType(FontAwesomeIconType.file_excel_o);
        add(excelButton);
    }

    protected abstract BaseJpaService<T> getJpaService();

    protected abstract SortableJpaServiceDataProvider<T> getDataProvider();
}
