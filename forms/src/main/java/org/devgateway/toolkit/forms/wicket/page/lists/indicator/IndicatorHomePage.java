package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall.ListDecadalRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason.ListRainSeasonPage;
import org.devgateway.toolkit.forms.wicket.styles.HomeStyles;
import org.wicketstuff.annotation.mount.MountPath;

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

        BootstrapBookmarkablePageLink<ListDecadalRainfallPage> rainfall = new BootstrapBookmarkablePageLink<>(
                "rainfall", ListDecadalRainfallPage.class, Buttons.Type.Default);
        rainfall.setLabel(new StringResourceModel("rainfall"));
        add(rainfall);

        BootstrapBookmarkablePageLink<ListRainSeasonPage> season = new BootstrapBookmarkablePageLink<>(
                "season", ListRainSeasonPage.class, Buttons.Type.Default);
        season.setLabel(new StringResourceModel("season"));
        add(season);
    }
}
