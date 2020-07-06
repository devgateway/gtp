package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractStatusableExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListDecadalRainfallPage;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;

import java.io.InputStream;

/**
 * @author Nadejda Mandrescu
 */
public class EditDecadalRainfallImportPage extends AbstractStatusableExcelImportPage<DecadalRainfall> {
    private static final long serialVersionUID = -5144558589415946153L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public EditDecadalRainfallImportPage(PageParameters parameters) {
        super(parameters);

        jpaService = decadalRainfallService;

        setListPage(ListDecadalRainfallPage.class);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        // TODO
        return null;
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        // TODO
    }
}
