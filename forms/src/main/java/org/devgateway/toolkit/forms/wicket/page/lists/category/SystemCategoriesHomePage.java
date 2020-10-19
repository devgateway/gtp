package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ANACIM_ROLES_STR;
import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR;
import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_EDITOR;
import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_MARKET_EDITOR;
import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListGTPMembersPage;
import org.devgateway.toolkit.forms.wicket.styles.HomeStyles;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(ROLE_EDITOR)
@MountPath(value = "/system-categories")
public class SystemCategoriesHomePage extends BasePage {
    private static final long serialVersionUID = 8435835519375264268L;

    /**
     * Construct.
     *
     * @param parameters current page parameters
     */
    public SystemCategoriesHomePage(PageParameters parameters) {
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

        BootstrapBookmarkablePageLink<ListLocalitiesPage> localities = new BootstrapBookmarkablePageLink<>(
                "localities", ListLocalitiesPage.class, Buttons.Type.Default);
        localities.setLabel(new StringResourceModel("localities"));
        add(localities);
        MetaDataRoleAuthorizationStrategy.authorize(localities, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);

        BootstrapBookmarkablePageLink<ListPluviometricPostPage> stations = new BootstrapBookmarkablePageLink<>(
                "pluviometricPosts", ListPluviometricPostPage.class, Buttons.Type.Default);
        stations.setLabel(new StringResourceModel("pluviometricPosts"));
        add(stations);
        MetaDataRoleAuthorizationStrategy.authorize(stations, Component.RENDER, ANACIM_ROLES_STR);

        BootstrapBookmarkablePageLink<ListRiversPage> rivers =
                new BootstrapBookmarkablePageLink<>("rivers", ListRiversPage.class, Buttons.Type.Default);
        rivers.setLabel(new StringResourceModel("rivers"));
        add(rivers);
        MetaDataRoleAuthorizationStrategy.authorize(rivers, Component.RENDER, ROLE_RIVER_LEVEL_EDITOR);

        BootstrapBookmarkablePageLink<ListRiverStationsPage> riverStations =
                new BootstrapBookmarkablePageLink<>("riverStations", ListRiverStationsPage.class, Buttons.Type.Default);
        riverStations.setLabel(new StringResourceModel("riverStations"));
        add(riverStations);
        MetaDataRoleAuthorizationStrategy.authorize(riverStations, Component.RENDER, ROLE_RIVER_LEVEL_EDITOR);

        BootstrapBookmarkablePageLink<ListMarketsPage> markets = new BootstrapBookmarkablePageLink<>(
                "markets", ListMarketsPage.class, Buttons.Type.Default);
        markets.setLabel(new StringResourceModel("markets"));
        add(markets);
        MetaDataRoleAuthorizationStrategy.authorize(markets, Component.RENDER, ROLE_MARKET_EDITOR);

        BootstrapBookmarkablePageLink<?> productTypes = new BootstrapBookmarkablePageLink<>(
                "productTypes", ListProductTypesPage.class, Buttons.Type.Default);
        productTypes.setLabel(new StringResourceModel("productTypes"));
        add(productTypes);
        MetaDataRoleAuthorizationStrategy.authorize(productTypes, Component.RENDER, ROLE_MARKET_EDITOR);

        BootstrapBookmarkablePageLink<?> products = new BootstrapBookmarkablePageLink<>(
                "products", ListProductsPage.class, Buttons.Type.Default);
        products.setLabel(new StringResourceModel("products"));
        add(products);
        MetaDataRoleAuthorizationStrategy.authorize(products, Component.RENDER, ROLE_MARKET_EDITOR);

        BootstrapBookmarkablePageLink<?> gtpMembers = new BootstrapBookmarkablePageLink<>(
                "gtpMembers", ListGTPMembersPage.class, Buttons.Type.Default);
        gtpMembers.setLabel(new StringResourceModel("gtpMembers"));
        add(gtpMembers);
        MetaDataRoleAuthorizationStrategy.authorize(gtpMembers, Component.RENDER, ANACIM_ROLES_STR);

        BootstrapBookmarkablePageLink<?> livestockDiseases = new BootstrapBookmarkablePageLink<>(
                "livestockDiseases", ListLivestockDiseasePage.class, Buttons.Type.Default);
        livestockDiseases.setLabel(new StringResourceModel("livestockDiseases"));
        add(livestockDiseases);
        MetaDataRoleAuthorizationStrategy.authorize(livestockDiseases, Component.RENDER, ROLE_DISEASE_SITUATION_EDITOR);
    }
}
