package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadDecadalRainfallLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractStatusableExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListDecadalRainfallPage;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.DecadalRainfallReader;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.DecadalRainfallService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;

import java.io.InputStream;

/**
 * @author Nadejda Mandrescu
 */
public class EditDecadalRainfallImportPage extends AbstractStatusableExcelImportPage<DecadalRainfall> {
    private static final long serialVersionUID = -5144558589415946153L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    @SpringBean
    private ZoneService zoneService;

    public EditDecadalRainfallImportPage(PageParameters parameters) {
        super(parameters);

        jpaService = decadalRainfallService;

        setListPage(ListDecadalRainfallPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        deleteButton.setVisibilityAllowed(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadDecadalRainfallLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        // TODO
    }
}
