package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadYearlyRainfallLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractStatusableExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListYearlyRainfallPage;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallReader;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;
import org.devgateway.toolkit.persistence.util.JPAUtil;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rainfall-upload")
public class EditYearlyRainfallImportPage extends AbstractStatusableExcelImportPage<YearlyRainfall> {
    private static final long serialVersionUID = -5144558589415946153L;

    @SpringBean
    private YearlyRainfallService yearlyRainfallService;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    public EditYearlyRainfallImportPage(PageParameters parameters) {
        super(parameters);

        jpaService = yearlyRainfallService;

        setListPage(ListYearlyRainfallPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        deleteButton.setVisibilityAllowed(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadYearlyRainfallLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        YearlyRainfall yearlyRainfall = editForm.getModelObject();
        List<PluviometricPost> pluviometricPosts = pluviometricPostService.findAll();

        YearlyRainfallReader reader = new YearlyRainfallReader(yearlyRainfall,
                Collections.emptyList(), pluviometricPosts);
        YearlyRainfall newEntity = reader.read(inputStream);

        JPAUtil.mergeSortedSet(
                newEntity.getStationDecadalRainfalls(), new TreeSet<>(yearlyRainfall.getStationDecadalRainfalls()),
                yearlyRainfall::addStationDecadalRainfall,
                (oldItem, newItem) -> {
                    oldItem.setRainfall(newItem.getRainfall());
                    oldItem.setRainyDaysCount(newItem.getRainyDaysCount());
                });
    }
}
