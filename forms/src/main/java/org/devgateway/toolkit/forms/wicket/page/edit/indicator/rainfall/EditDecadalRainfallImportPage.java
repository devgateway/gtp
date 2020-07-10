package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.model.Model;
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
import org.devgateway.toolkit.persistence.service.location.ZoneService;
import org.devgateway.toolkit.persistence.util.JPAUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    protected StringResourceModel getImportHelp(String id) {
        DecadalRainfall dr = editForm.getModelObject();
        int startDay = dr.getDecadal().startDay();
        int endDay = startDay + dr.lengthOfDecadal() - 1;
        String days = IntStream.rangeClosed(startDay, endDay).boxed().map(Object::toString)
                .collect(Collectors.joining(", "));

        Map<String, Object> params = new HashMap<>();
        params.put("decadalDays", days);
        params.put("decadalLastDay", endDay);
        return new StringResourceModel(id, this, Model.ofMap(params));
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadDecadalRainfallLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        DecadalRainfall decadalRainfall = editForm.getModelObject();
        List<Zone> zones = zoneService.findAll();
        List<PluviometricPost> pluviometricPosts = pluviometricPostService.findAll();

        DecadalRainfallReader reader = new DecadalRainfallReader(decadalRainfall, zones, pluviometricPosts);
        DecadalRainfall newEntity =  reader.read(inputStream);

        JPAUtil.mergeSortedSet(
                newEntity.getPostRainfalls(), new TreeSet<>(decadalRainfall.getPostRainfalls()),
                decadalRainfall::addPostRainfall,
                (oldItem, newItem) -> {
                    JPAUtil.mergeSortedSet(
                            newItem.getRainfalls(), new TreeSet<>(oldItem.getRainfalls()),
                            oldItem::addRainfall,
                            (oldRainItem, newRainItem) -> oldRainItem.setRain(newRainItem.getRain()));
                    if (Boolean.TRUE.equals(oldItem.getNoData()) && !oldItem.getRainfalls().isEmpty()) {
                        oldItem.setNoData(false);
                    }
                });
    }
}
