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

        BootstrapBookmarkablePageLink<SystemCategoriesHomePage> stations = new BootstrapBookmarkablePageLink<>(
                "stations", SystemCategoriesHomePage.class, Buttons.Type.Default);
        stations.setLabel(new StringResourceModel("stations"));
        add(stations);

        BootstrapBookmarkablePageLink<SystemCategoriesHomePage> referenceRain = new BootstrapBookmarkablePageLink<>(
                "referenceRain", SystemCategoriesHomePage.class, Buttons.Type.Default);
        referenceRain.setLabel(new StringResourceModel("referenceRain"));
        add(referenceRain);
    }
}
