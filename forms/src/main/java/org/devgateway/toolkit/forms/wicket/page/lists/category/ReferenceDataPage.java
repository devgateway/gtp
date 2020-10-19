package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_RAINFALL_EDITOR;
import static org.devgateway.toolkit.forms.security.SecurityConstants.Roles.ROLE_RAINFALL_SEASON_EDITOR;
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
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainLevelReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainSeasonStartReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRiverStationYearlyLevelsReferencesPage;
import org.devgateway.toolkit.forms.wicket.styles.HomeStyles;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation({
        ROLE_RAINFALL_EDITOR,
        ROLE_RAINFALL_SEASON_EDITOR,
        SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR,
})
@MountPath(value = "/reference-data")
public class ReferenceDataPage extends BasePage {
    private static final long serialVersionUID = -3655744947953389042L;

    public ReferenceDataPage(PageParameters parameters) {
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

        BootstrapBookmarkablePageLink<?> referenceRain = new BootstrapBookmarkablePageLink<>(
                "referenceRain", ListRainLevelReferencePage.class, Buttons.Type.Default);
        referenceRain.setLabel(new StringResourceModel("referenceRain"));
        add(referenceRain);
        MetaDataRoleAuthorizationStrategy.authorize(referenceRain, Component.RENDER, ROLE_RAINFALL_EDITOR);

        BootstrapBookmarkablePageLink<?> referenceSeason = new BootstrapBookmarkablePageLink<>(
                "referenceSeason", ListRainSeasonStartReferencePage.class, Buttons.Type.Default);
        referenceSeason.setLabel(new StringResourceModel("referenceSeason"));
        add(referenceSeason);
        MetaDataRoleAuthorizationStrategy.authorize(referenceSeason, Component.RENDER, ROLE_RAINFALL_SEASON_EDITOR);

        BootstrapBookmarkablePageLink<?> riverLevelRefs = new BootstrapBookmarkablePageLink<>(
                "riverLevelRefs", ListRiverStationYearlyLevelsReferencesPage.class, Buttons.Type.Default);
        riverLevelRefs.setLabel(new StringResourceModel("riverLevelRefs"));
        add(riverLevelRefs);
        MetaDataRoleAuthorizationStrategy.authorize(riverLevelRefs, Component.RENDER, ROLE_RIVER_LEVEL_EDITOR);
    }
}
