package org.devgateway.toolkit.forms.wicket.page.dashboard;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.TooltipBehavior;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.SSAuthenticatedWebSession;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.status.AnnualGTPBulletinStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.DiseasesStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.GTPBulletinStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.ProductPriceAndAvailabilityStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.RainSeasonStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.RainfallMapStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.RainfallStatusTable;
import org.devgateway.toolkit.forms.wicket.components.status.RiverStationLevelStatusTable;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListAnnualGTPReportsPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListGTPBulletinPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.disease.ListDiseaseYearlySituationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.market.ListProductYearlyPricesPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListYearlyRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap.ListDecadalRainfallMapPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason.ListRainSeasonPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.riverlevel.ListRiverStationYearlyLevelsPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.status.AnnualGTPBulletinProgress;
import org.devgateway.toolkit.persistence.status.DatasetProgress;
import org.devgateway.toolkit.persistence.status.DiseasesProgress;
import org.devgateway.toolkit.persistence.status.GTPBulletinProgress;
import org.devgateway.toolkit.persistence.status.ProductPriceAndAvailabilityProgress;
import org.devgateway.toolkit.persistence.status.RainSeasonYearProgress;
import org.devgateway.toolkit.persistence.status.RainfallMapProgress;
import org.devgateway.toolkit.persistence.status.RainfallYearProgress;
import org.devgateway.toolkit.persistence.status.RiverStationsYearProgress;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.AnnualGTPReportService;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.GTPBulletinService;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;
import org.devgateway.toolkit.persistence.service.indicator.rainfallMap.DecadalRainfallMapService;
import org.devgateway.toolkit.persistence.service.indicator.river.RiverStationYearlyLevelsService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Octavian Ciubotaru
 */
@MountPath("/dashboard")
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
public class DataEntryDashboardPage extends BasePage {

    private final IModel<Integer> yearModel;

    @SpringBean
    private AdminSettingsService adminSettingsService;

    @SpringBean
    private YearlyRainfallService yearlyRainfallService;

    @SpringBean
    private DecadalRainfallMapService decadalRainfallMapService;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    @SpringBean
    private RiverStationYearlyLevelsService riverStationYearlyLevelsService;

    @SpringBean
    private ProductYearlyPricesService productYearlyPricesService;

    @SpringBean
    private GTPBulletinService gtpBulletinService;

    @SpringBean
    private AnnualGTPReportService annualGTPReportService;

    @SpringBean
    private DiseaseYearlySituationService diseaseYearlySituationService;

    private class RainfallProgressSection extends ProgressSection<RainfallYearProgress> {

        RainfallProgressSection() {
            super(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR);
        }

        @Override
        public IModel<RainfallYearProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> yearlyRainfallService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<RainfallYearProgress> model) {
            return new RainfallStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListYearlyRainfallPage.class, forYear(yearModel));
        }
    }

    private class RainfallMapProgressSection extends ProgressSection<RainfallMapProgress> {

        RainfallMapProgressSection() {
            super(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR);
        }

        @Override
        public IModel<RainfallMapProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> decadalRainfallMapService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<RainfallMapProgress> model) {
            return new RainfallMapStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListDecadalRainfallMapPage.class, forYear(yearModel));
        }
    }

    private class RainSeasonProgressSection extends ProgressSection<RainSeasonYearProgress> {

        RainSeasonProgressSection() {
            super(SecurityConstants.Roles.ROLE_RAINFALL_SEASON_EDITOR);
        }

        @Override
        public IModel<RainSeasonYearProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(
                    () -> pluviometricPostRainSeasonService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<RainSeasonYearProgress> model) {
            return new RainSeasonStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListRainSeasonPage.class, forYear(yearModel));
        }
    }

    private class RiverStationLevelProgressSection extends ProgressSection<RiverStationsYearProgress> {

        RiverStationLevelProgressSection() {
            super(SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR);
        }

        @Override
        public IModel<RiverStationsYearProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> riverStationYearlyLevelsService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<RiverStationsYearProgress> model) {
            return new RiverStationLevelStatusTable(id, model, yearModel);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListRiverStationYearlyLevelsPage.class, forYear(yearModel));
        }
    }

    private class ProductPriceAndAvailabilityProgressSection
            extends ProgressSection<ProductPriceAndAvailabilityProgress> {

        ProductPriceAndAvailabilityProgressSection() {
            super(SecurityConstants.Roles.ROLE_MARKET_EDITOR);
        }

        @Override
        public IModel<ProductPriceAndAvailabilityProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> productYearlyPricesService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<ProductPriceAndAvailabilityProgress> model) {
            return new ProductPriceAndAvailabilityStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListProductYearlyPricesPage.class, forYear(yearModel));
        }
    }

    private class GTPBulletinProgressSection extends ProgressSection<GTPBulletinProgress> {

        GTPBulletinProgressSection() {
            super(SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR);
        }

        @Override
        public IModel<GTPBulletinProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> gtpBulletinService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<GTPBulletinProgress> model) {
            return new GTPBulletinStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListGTPBulletinPage.class, forYear(yearModel));
        }
    }

    private class AnnualGTPBulletinProgressSection extends ProgressSection<AnnualGTPBulletinProgress> {

        AnnualGTPBulletinProgressSection() {
            super(SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR);
        }

        @Override
        public IModel<AnnualGTPBulletinProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> annualGTPReportService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<AnnualGTPBulletinProgress> model) {
            return new AnnualGTPBulletinStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListAnnualGTPReportsPage.class, null);
        }
    }

    private class DiseasesProgressSection extends ProgressSection<DiseasesProgress> {

        DiseasesProgressSection() {
            super(SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR);
        }

        @Override
        public IModel<DiseasesProgress> getDatasetProgress() {
            return LoadableDetachableModel.of(() -> diseaseYearlySituationService.getProgress(yearModel.getObject()));
        }

        @Override
        public Component createDetailedProgress(String id, IModel<DiseasesProgress> model) {
            return new DiseasesStatusTable(id, model);
        }

        @Override
        public Pair<Class<? extends Page>, PageParameters> getEditPage() {
            return Pair.of(ListDiseaseYearlySituationPage.class, null);
        }
    }

    /**
     * Construct.
     *
     * @param parameters current page parameters
     */
    public DataEntryDashboardPage(PageParameters parameters) {
        super(parameters);

        Form<Object> form = new Form<>("form");
        form.setOutputMarkupId(true);
        add(form);

        yearModel = Model.of(LocalDate.now(AD3Clock.systemDefaultZone()).getYear());

        List<ProgressSection> sections = new ArrayList<>();
        sections.add(new RainfallProgressSection());
        sections.add(new RainfallMapProgressSection());
        sections.add(new RainSeasonProgressSection());
        sections.add(new RiverStationLevelProgressSection());
        sections.add(new ProductPriceAndAvailabilityProgressSection());
        sections.add(new GTPBulletinProgressSection());
        sections.add(new AnnualGTPBulletinProgressSection());
        sections.add(new DiseasesProgressSection());

        ListView<ProgressSection> datasetsListView = new ListView<ProgressSection>("datasets", sections) {

            @Override
            protected void populateItem(ListItem<ProgressSection> item) {
                ProgressSection progressSection = item.getModelObject();
                IModel<DatasetProgress> datasetProgress = progressSection.getDatasetProgress();

                Component details = progressSection.createDetailedProgress("details", datasetProgress);
                details.setVisibilityAllowed(false);
                details.setOutputMarkupPlaceholderTag(true);
                item.add(details);

                ExpandButton expand = new ExpandButton("expand", datasetProgress) {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        super.onSubmit(target);
                        details.setVisibilityAllowed(!details.isVisibilityAllowed());
                        target.add(details, this);
                    }

                    @Override
                    protected void onConfigure() {
                        setIconType(details.isVisibilityAllowed()
                                ? FontAwesomeIconType.chevron_up
                                : FontAwesomeIconType.chevron_down);
                        super.onConfigure();
                    }
                };
                expand.setOutputMarkupId(true);
                item.add(expand);



                Pair<Class<? extends Page>, PageParameters> editPageRef = progressSection.getEditPage();
                item.add(new BootstrapBookmarkablePageLink<>(
                        "edit", editPageRef.getKey(), editPageRef.getValue(), Buttons.Type.Link)
                        .setIconType(FontAwesomeIconType.edit)
                        .setEnabled(hasRole(progressSection.getRole())));
            }
        };
        form.add(datasetsListView);

        List<Integer> years = adminSettingsService.getYears().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        Select2ChoiceBootstrapFormComponent<Integer> year = new Select2ChoiceBootstrapFormComponent<Integer>(
                "year", new GenericChoiceProvider<>(years), yearModel) {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                super.onUpdate(target);
                target.add(form);
            }

            @Override
            protected void onInitialize() {
                super.onInitialize();
                field.getSettings().setAllowClear(false);
            }
        };
        form.add(year);

        form.add(new WebMarkupContainer("progressHelp")
                .add(new TooltipBehavior(new StringResourceModel("progressHelp"))));
    }

    private boolean hasRole(String role) {
        return SSAuthenticatedWebSession.getSSAuthenticatedWebSession().getRoles().hasRole(role);
    }
}
