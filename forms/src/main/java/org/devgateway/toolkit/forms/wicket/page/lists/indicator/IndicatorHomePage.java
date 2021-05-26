package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListAnnualGTPReportsPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListGTPBulletinPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.disease.ListDiseaseYearlySituationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.market.ListProductYearlyPricesPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListYearlyRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap.ListDecadalRainfallMapPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason.ListRainSeasonPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.riverlevel.ListRiverStationYearlyLevelsPage;
import org.devgateway.toolkit.forms.wicket.styles.HomeStyles;
import org.wicketstuff.annotation.mount.MountPath;

import static org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy.authorize;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/indicators")
public class IndicatorHomePage extends BasePage {
    private static final long serialVersionUID = 5979867651870077641L;

    /**
     * Construct.
     *
     * @param parameters current page parameters
     */
    public IndicatorHomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        // Load Home Page Styles
        response.render(CssHeaderItem.forReference(HomeStyles.INSTANCE));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BootstrapBookmarkablePageLink<ListYearlyRainfallPage> rainfall = new BootstrapBookmarkablePageLink<>(
                "rainfall", ListYearlyRainfallPage.class, Buttons.Type.Default);
        rainfall.setLabel(new StringResourceModel("rainfall"));
        authorize(rainfall, Component.RENDER, SecurityConstants.Roles.ROLE_RAINFALL_EDITOR);
        add(rainfall);

        BootstrapBookmarkablePageLink<ListDecadalRainfallMapPage> rainfallMap = new BootstrapBookmarkablePageLink<>(
                "rainfallMap", ListDecadalRainfallMapPage.class, Buttons.Type.Default);
        rainfallMap.setLabel(new StringResourceModel("rainfallMap"));
        authorize(rainfallMap, Component.RENDER, SecurityConstants.Roles.ROLE_RAINFALL_EDITOR);
        add(rainfallMap);

        BootstrapBookmarkablePageLink<ListRainSeasonPage> season = new BootstrapBookmarkablePageLink<>(
                "season", ListRainSeasonPage.class, Buttons.Type.Default);
        season.setLabel(new StringResourceModel("season"));
        authorize(season, Component.RENDER, SecurityConstants.Roles.ROLE_RAINFALL_SEASON_EDITOR);
        add(season);

        BootstrapBookmarkablePageLink<ListRiverStationYearlyLevelsPage> riverLevels =
                new BootstrapBookmarkablePageLink<>("riverLevels", ListRiverStationYearlyLevelsPage.class,
                        Buttons.Type.Default);
        riverLevels.setLabel(new StringResourceModel("riverLevels"));
        authorize(riverLevels, Component.RENDER, SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR);
        add(riverLevels);

        BootstrapBookmarkablePageLink<?> productYearlyPrices =
                new BootstrapBookmarkablePageLink<>("productYearlyPrices", ListProductYearlyPricesPage.class,
                        Buttons.Type.Default);
        productYearlyPrices.setLabel(new StringResourceModel("productYearlyPrices"));
        authorize(productYearlyPrices, Component.RENDER, SecurityConstants.Roles.ROLE_MARKET_EDITOR);
        add(productYearlyPrices);

        BootstrapBookmarkablePageLink<?> gtpBulletins =
                new BootstrapBookmarkablePageLink<>("gtpBulletins", ListGTPBulletinPage.class,
                        Buttons.Type.Default);
        gtpBulletins.setLabel(new StringResourceModel("gtpBulletins"));
        authorize(gtpBulletins, Component.RENDER, SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR);
        add(gtpBulletins);

        BootstrapBookmarkablePageLink<?> annualGTPReports =
                new BootstrapBookmarkablePageLink<>("annualGTPReports", ListAnnualGTPReportsPage.class,
                        Buttons.Type.Default);
        annualGTPReports.setLabel(new StringResourceModel("annualGTPReports"));
        authorize(annualGTPReports, Component.RENDER, SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR);
        add(annualGTPReports);

        BootstrapBookmarkablePageLink<?> diseaseSituations =
                new BootstrapBookmarkablePageLink<>("diseaseSituations", ListDiseaseYearlySituationPage.class,
                        Buttons.Type.Default);
        diseaseSituations.setLabel(new StringResourceModel("diseaseSituations"));
        authorize(diseaseSituations, Component.RENDER, SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR);
        add(diseaseSituations);
    }
}
