package org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainLevelReferencePage;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;

import java.io.InputStream;

/**
 * @author Nadejda Mandrescu
 */
public class EditRainLevelReferenceImportPage extends AbstractExcelImportPage<RainLevelReference> {
    private static final long serialVersionUID = 4682203609872135809L;

    @SpringBean
    private RainLevelReferenceService rainLevelReferenceService;

    public EditRainLevelReferenceImportPage(PageParameters parameters) {
        super(parameters);
        jpaService = rainLevelReferenceService;
        setListPage(ListRainLevelReferencePage.class);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return null;
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {

    }
}
