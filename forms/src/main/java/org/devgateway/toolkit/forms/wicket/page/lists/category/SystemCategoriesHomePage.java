package org.devgateway.toolkit.forms.wicket.page.lists.category;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.styles.HomeStyles;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
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

        BootstrapBookmarkablePageLink<ListPluviometricPostPage> stations = new BootstrapBookmarkablePageLink<>(
                "pluviometricPosts", ListPluviometricPostPage.class, Buttons.Type.Default);
        stations.setLabel(new StringResourceModel("pluviometricPosts"));
        add(stations);

        BootstrapBookmarkablePageLink<ListRiversPage> rivers =
                new BootstrapBookmarkablePageLink<>("rivers", ListRiversPage.class, Buttons.Type.Default);
        rivers.setLabel(new StringResourceModel("rivers"));
        add(rivers);

        BootstrapBookmarkablePageLink<ListRiverStationsPage> riverStations =
                new BootstrapBookmarkablePageLink<>("riverStations", ListRiverStationsPage.class, Buttons.Type.Default);
        riverStations.setLabel(new StringResourceModel("riverStations"));
        add(riverStations);

        BootstrapBookmarkablePageLink<ListMarketsPage> markets = new BootstrapBookmarkablePageLink<>(
                "markets", ListMarketsPage.class, Buttons.Type.Default);
        markets.setLabel(new StringResourceModel("markets"));
        add(markets);

        BootstrapBookmarkablePageLink<?> productTypes = new BootstrapBookmarkablePageLink<>(
                "productTypes", ListProductTypesPage.class, Buttons.Type.Default);
        productTypes.setLabel(new StringResourceModel("productTypes"));
        add(productTypes);

        BootstrapBookmarkablePageLink<?> products = new BootstrapBookmarkablePageLink<>(
                "products", ListProductsPage.class, Buttons.Type.Default);
        products.setLabel(new StringResourceModel("products"));
        add(products);
    }
}
