package org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadRainLevelReferenceLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainLevelReferencePage;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.location.ZoneService;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceReader;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;
import org.devgateway.toolkit.persistence.util.JPAUtil;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.InputStream;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rain-level-reference-upload")
public class EditRainLevelReferenceImportPage extends AbstractExcelImportPage<RainLevelReference> {
    private static final long serialVersionUID = 4682203609872135809L;

    @SpringBean
    private RainLevelReferenceService rainLevelReferenceService;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    @SpringBean
    private ZoneService zoneService;

    public EditRainLevelReferenceImportPage(PageParameters parameters) {
        super(parameters);
        jpaService = rainLevelReferenceService;
        setListPage(ListRainLevelReferencePage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        deleteButton.setVisibilityAllowed(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadRainLevelReferenceLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        RainLevelReference rainReference = editForm.getModelObject();

        List<Zone> zones = zoneService.findAll();
        List<PluviometricPost> pluviometricPosts = pluviometricPostService.findAll();

        RainLevelReferenceReader reader = new RainLevelReferenceReader(zones, pluviometricPosts);
        RainLevelReference newEntity = reader.read(inputStream);

        JPAUtil.mergeSortedSet(
                newEntity.getPostRainReferences(), new TreeSet<>(rainReference.getPostRainReferences()),
                rainReference::addPostReference,
                (oldItem, newItem) -> JPAUtil.mergeSortedSet(
                        newItem.getRainLevelMonthReferences(), new TreeSet<>(oldItem.getRainLevelMonthReferences()),
                        oldItem::addRainLevelMonthReference,
                        (oldRainItem, newRainItem) -> oldRainItem.setRain(newRainItem.getRain()))
        );

    }
}
