package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.models.WrapperNullableModel;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListAnnualGTPReportsPage;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.AnnualGTPReportService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_ANNUAL_REPORT_EDITOR)
@MountPath("/annual-gtp-report")
public class EditAnnualGTPReportPage extends AbstractEditPage<AnnualGTPReport> {
    private static final long serialVersionUID = 4431513836472016841L;

    @SpringBean
    private AnnualGTPReportService service;

    public EditAnnualGTPReportPage(PageParameters parameters) {
        super(parameters);

        setListPage(ListAnnualGTPReportsPage.class);
        jpaService = service;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.add(new TextFieldBootstrapFormComponent<>("year").setEnabled(false));
        editForm.add(new TextFieldBootstrapFormComponent<>("department",
                new WrapperNullableModel(
                        new PropertyModel<String>(editForm.getModel(), "department.name"),
                        new StringResourceModel("national", EditAnnualGTPReportPage.this)))
                .setEnabled(false));

        editForm.add(new FileInputBootstrapFormComponent("uploads").maxFiles(1).allowedFileExtensions("pdf"));

        deleteButton.setVisibilityAllowed(false);
    }
}
