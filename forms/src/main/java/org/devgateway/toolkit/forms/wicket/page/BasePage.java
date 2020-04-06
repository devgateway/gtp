/*******************************************************************************
 * Copyright (c) 2015 Development Gateway, Inc and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 *
 * Contributors:
 * Development Gateway - initial API and implementation
 *******************************************************************************/
package org.devgateway.toolkit.forms.wicket.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonList;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuDivider;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton;
import de.agilecoders.wicket.core.markup.html.references.RespondJavaScriptReference;
import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapCssReference;
import de.agilecoders.wicket.core.util.CssClassNames;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.components.navigation.DropDownSubMenu;
import org.devgateway.toolkit.forms.wicket.page.edit.EditGisSettingsPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAOIIndicatorDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAgriculturalContentFormPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAgriculturalWomenDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListConsumptionDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListDepartmentIndicatorPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListFoodLossDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListIndicatorGroupPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListIndicatorMetadataFormPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListMarketPriceDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListMicrodataLinkFormPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListNationalIndicatorFormPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListOrganizationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListPartnerPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListPovertyIndicatorDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListProductionDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListRapidLinkFormPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListRegionIndicatorPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListUserPage;
import org.devgateway.toolkit.forms.wicket.page.user.EditUserPage;
import org.devgateway.toolkit.forms.wicket.page.user.LogoutPage;
import org.devgateway.toolkit.forms.wicket.styles.BaseStyles;
import org.devgateway.toolkit.persistence.dao.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base wicket-bootstrap {@link org.apache.wicket.Page}
 *
 * @author miha
 */
public abstract class BasePage extends GenericWebPage<Void> {
    private static final long serialVersionUID = -4179591658828697452L;

    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    private TransparentWebMarkupContainer mainContainer;

    private Header mainHeader;

    private Footer mainFooter;

    protected Label pageTitle;

    private Navbar navbar;

    protected NotificationPanel feedbackPanel;

    /**
     * Determines if this page has a fluid container for the content or not.
     */
    public Boolean fluidContainer() {
        return false;
    }

    public static class HALRedirectPage extends RedirectPage {
        private static final long serialVersionUID = -750983217518258464L;

        public HALRedirectPage() {
            super(WebApplication.get().getServletContext().getContextPath() + "/api/browser/");
        }

    }

    public static class JminixRedirectPage extends RedirectPage {
        private static final long serialVersionUID = -750983217518258464L;

        public JminixRedirectPage() {
            super(WebApplication.get().getServletContext().getContextPath() + "/jminix/");
        }

    }

    public static class UIRedirectPage extends RedirectPage {
        private static final long serialVersionUID = -750983217518258464L;

        public UIRedirectPage() {
            super(WebApplication.get().getServletContext().getContextPath() + "/index.html");
        }
    }

    /**
     * Construct.
     *
     * @param parameters current page parameters
     */
    public BasePage(final PageParameters parameters) {
        super(parameters);

        add(new HtmlTag("html"));

        // Add javascript files.
        add(new HeaderResponseContainer("scripts-container", "scripts-bucket"));

        feedbackPanel = createFeedbackPanel();
        add(feedbackPanel);

        mainContainer = new TransparentWebMarkupContainer("mainContainer");
        add(mainContainer);

        // Set the bootstrap container class.
        // @see https://getbootstrap.com/css/#grid
        if (fluidContainer()) {
            mainContainer.add(new CssClassNameAppender(CssClassNames.Grid.containerFluid));
        } else {
            mainContainer.add(new CssClassNameAppender(CssClassNames.Grid.container));
        }

        mainHeader = new Header("mainHeader", this.getPageParameters());
        add(mainHeader);

        navbar = newNavbar("navbar");
        mainHeader.add(navbar);

        // Add information about navbar position on mainHeader element.
        if (navbar.getPosition().equals(Navbar.Position.DEFAULT)) {
            mainHeader.add(new CssClassNameAppender("with-navbar-default"));
        } else {
            mainHeader.add(new CssClassNameAppender("with-" + navbar.getPosition().cssClassName()));
        }

        mainFooter = new Footer("mainFooter");
        add(mainFooter);

        pageTitle = new Label("pageTitle", new ResourceModel("page.title"));
        add(pageTitle);

    }

    protected NotificationPanel createFeedbackPanel() {
        final NotificationPanel notificationPanel = new NotificationPanel("feedback");
        notificationPanel.setOutputMarkupId(true);
        return notificationPanel;
    }

    public NavbarDropDownButton newLanguageMenu() {
        final NavbarDropDownButton languageDropDown =
                new NavbarDropDownButton(new StringResourceModel("navbar.lang", this, null)) {

                    @Override
                    protected List<AbstractLink> newSubMenuButtons(final String buttonMarkupId) {
                        return WebConstants.AVAILABLE_LOCALES.stream()
                                .map(ChangeLanguageLink::new)
                                .collect(Collectors.toList());
                    }
                };
        languageDropDown.setIconType(FontAwesomeIconType.flag);
        return languageDropDown;
    }

    private static class ChangeLanguageLink extends BootstrapLink<Locale> {

        ChangeLanguageLink(Locale locale) {
            super(ButtonList.getButtonMarkupId(), Model.of(locale), Buttons.Type.Menu);
        }

        protected Component newLabel(final String markupId) {
            Locale locale = getModelObject();
            String language = StringUtils.capitalize(locale.getDisplayName(locale));
            Label label = new Label(markupId, Model.of(language));
            label.setRenderBodyOnly(true);
            return label;
        }

        @Override
        public void onClick() {
            WebSession.get().setLocale(getModelObject());
        }
    }

    protected NavbarButton<LogoutPage> newLogoutMenu() {
        // logout menu
        final NavbarButton<LogoutPage> logoutMenu =
                new NavbarButton<LogoutPage>(LogoutPage.class, Model.of(""));
        logoutMenu.setIconType(FontAwesomeIconType.sign_out);
        logoutMenu.add(AttributeModifier.append("title", new StringResourceModel("navbar.logout", this, null)));
        MetaDataRoleAuthorizationStrategy.authorize(logoutMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);

        return logoutMenu;
    }

    protected NavbarButton<EditUserPage> newAccountMenu() {
        final PageParameters pageParametersForAccountPage = new PageParameters();
        final Person person = SecurityUtil.getCurrentAuthenticatedPerson();
        // account menu
        Model<String> account = null;
        final NavbarButton<EditUserPage> accountMenu =
                new NavbarButton<>(EditUserPage.class, pageParametersForAccountPage, account);

        if (person != null) {
            //account = Model.of(person.getFirstName());
            account = Model.of("");
            pageParametersForAccountPage.add(WebConstants.PARAM_ID, person.getId());
            accountMenu.add(AttributeModifier.append("title", Model.of(person.getFirstName())));
        }


        accountMenu.setIconType(FontAwesomeIconType.user);
        MetaDataRoleAuthorizationStrategy.authorize(accountMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);
        return accountMenu;
    }

    protected NavbarButton<Homepage> newHomeMenu() {
        // home
        NavbarButton<Homepage> homeMenu = new NavbarButton<>(Homepage.class,
                new StringResourceModel("home", BasePage.this));
        homeMenu.setIconType(FontAwesomeIconType.home);
        MetaDataRoleAuthorizationStrategy.authorize(homeMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);
        return homeMenu;
    }


    protected NavbarDropDownButton newAdminMenu() {

        // admin menu
        NavbarDropDownButton adminMenu = new NavbarDropDownButton(new StringResourceModel("navbar.admin", this, null)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String arg0) {
                final List<AbstractLink> list = new ArrayList<>();

                list.add(new MenuBookmarkablePageLink<>(ListUserPage.class, null,
                        new StringResourceModel("navbar.users", this, null))
                        .setIconType(FontAwesomeIconType.users));

                list.add(new MenuBookmarkablePageLink<>(ListOrganizationPage.class, null,
                        new StringResourceModel("navbar.orgs", this, null)).setIconType(FontAwesomeIconType.tags));

                list.add(new MenuDivider());

                list.add(new MenuBookmarkablePageLink<>(EditAdminSettingsPage.class,
                        new StringResourceModel("navbar.adminSettings", BasePage.this, null))
                        .setIconType(FontAwesomeIconType.briefcase));

                list.add(new MenuDivider());

                final MenuBookmarkablePageLink<UIRedirectPage> uiBrowserLink =
                        new MenuBookmarkablePageLink<UIRedirectPage>(
                                UIRedirectPage.class, null, new StringResourceModel("navbar.ui", this, null)) {
                            private static final long serialVersionUID = 1L;

                            @Override
                            protected void onComponentTag(final ComponentTag tag) {
                                super.onComponentTag(tag);
                                tag.put("target", "_blank");
                            }
                        };
                uiBrowserLink.setIconType(FontAwesomeIconType.rocket).setEnabled(true);
                list.add(uiBrowserLink);

                return list;
            }
        };

        adminMenu.setIconType(FontAwesomeIconType.cog);
        MetaDataRoleAuthorizationStrategy.authorize(adminMenu, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);

        return adminMenu;
    }

    protected NavbarDropDownButton newCMMenu() {

        // admin menu
        NavbarDropDownButton cmMenu = new NavbarDropDownButton(new StringResourceModel("navbar.cm", this, null)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String arg0) {
                final List<AbstractLink> list = new ArrayList<>();

                list.add(new MenuBookmarkablePageLink<ListRapidLinkFormPage>(ListRapidLinkFormPage.class, null,
                        new StringResourceModel("navbar.rapidLink", this, null))
                        .setIconType(FontAwesomeIconType.link));

                list.add(new MenuBookmarkablePageLink<ListAgriculturalContentFormPage>(
                        ListAgriculturalContentFormPage.class, null,
                        new StringResourceModel("navbar.agriculturalContent", this, null))
                        .setIconType(FontAwesomeIconType.list_ul));

                list.add(new MenuBookmarkablePageLink<>(ListIndicatorGroupPage.class, null,
                        new StringResourceModel("navbar.indicatorGroup", this, null))
                        .setIconType(FontAwesomeIconType.sort_amount_desc));

                list.add(new MenuBookmarkablePageLink<ListIndicatorMetadataFormPage>(
                        ListIndicatorMetadataFormPage.class, null, new StringResourceModel("navbar.indicatorMetadata",
                        this, null)).setIconType(FontAwesomeIconType.bolt));

                list.add(new MenuBookmarkablePageLink<ListMicrodataLinkFormPage>(ListMicrodataLinkFormPage.class, null,
                        new StringResourceModel("navbar.microdata", this, null))
                        .setIconType(FontAwesomeIconType.external_link));

                list.add(new MenuBookmarkablePageLink<>(ListPartnerPage.class, null,
                        new StringResourceModel("navbar.partners", this, null))
                        .setIconType(FontAwesomeIconType.handshake_o));

                return list;
            }
        };

        cmMenu.setIconType(FontAwesomeIconType.toggle_on);
        MetaDataRoleAuthorizationStrategy.authorize(cmMenu, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);

        return cmMenu;
    }

    protected NavbarDropDownButton newDatasetMenu() {

        // upload menu
        NavbarDropDownButton datasetMenu = new NavbarDropDownButton(
                new StringResourceModel("navbar.dataset", this, null)) {
            private static final long serialVersionUID = 2L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String arg0) {
                final List<AbstractLink> list = new ArrayList<>();

                list.add(new MenuBookmarkablePageLink<ListProductionDatasetPage>(ListProductionDatasetPage.class, null,
                        new StringResourceModel("navbar.production", this, null))
                        .setIconType(FontAwesomeIconType.pagelines));

                list.add(new MenuBookmarkablePageLink<ListConsumptionDatasetPage>(
                        ListConsumptionDatasetPage.class, null,
                        new StringResourceModel("navbar.consumption", this, null))
                        .setIconType(FontAwesomeIconType.shopping_basket));

                list.add(new MenuBookmarkablePageLink<ListMarketPriceDatasetPage>(ListMarketPriceDatasetPage.class,
                        null, new StringResourceModel("navbar.marketPrice", this, null))
                        .setIconType(FontAwesomeIconType.usd));

                return list;
            }
        };

        datasetMenu.setIconType(FontAwesomeIconType.upload);
        MetaDataRoleAuthorizationStrategy.authorize(datasetMenu, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);
        MetaDataRoleAuthorizationStrategy.authorize(datasetMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);

        return datasetMenu;
    }

    protected NavbarDropDownButton newIndicatorMenu() {

        // upload menu
        NavbarDropDownButton indicatorMenu = new NavbarDropDownButton(
                new StringResourceModel("navbar.indicator", this, null)) {
            private static final long serialVersionUID = 2L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String arg0) {
                final List<AbstractLink> list = new ArrayList<>();

                DropDownSubMenu sdgMenu = new DropDownSubMenu(
                        new StringResourceModel("navbar.sdgs", this, null)) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean isActive(Component item) {
                        return false;
                    }

                    @Override
                    protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
                        List<AbstractLink> list = new ArrayList<>();

                        list.add(new MenuBookmarkablePageLink<ListPovertyIndicatorDatasetPage>(
                                ListPovertyIndicatorDatasetPage.class, null,
                                new StringResourceModel("navbar.poverty", this, null))
                                .setIconType(FontAwesomeIconType.group));

                        list.add(new MenuBookmarkablePageLink<ListAgriculturalWomenDatasetPage>(
                                ListAgriculturalWomenDatasetPage.class, null,
                                new StringResourceModel("navbar.agriculturalWomen", this, null))
                                .setIconType(FontAwesomeIconType.female));

                        list.add(new MenuBookmarkablePageLink<ListFoodLossDatasetPage>(
                                ListFoodLossDatasetPage.class, null,
                                new StringResourceModel("navbar.foodLoss", this, null))
                                .setIconType(FontAwesomeIconType.bolt));


                        list.add(new MenuBookmarkablePageLink<ListAOIIndicatorDatasetPage>(
                                ListAOIIndicatorDatasetPage.class, null,
                                new StringResourceModel("navbar.aoi", this, null))
                                .setIconType(FontAwesomeIconType.arrows_alt));

                        return list;
                    }
                };
                sdgMenu.setIconType(FontAwesomeIconType.th_large);
                list.add(sdgMenu);

                list.add(new MenuBookmarkablePageLink<ListNationalIndicatorFormPage>(
                        ListNationalIndicatorFormPage.class, null,
                        new StringResourceModel("navbar.nationalIndicator", this, null))
                        .setIconType(FontAwesomeIconType.area_chart));

                list.add(new MenuDivider());




                return list;
            }
        };

        indicatorMenu.setIconType(FontAwesomeIconType.upload);
        MetaDataRoleAuthorizationStrategy.authorize(indicatorMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_ADMIN);
        MetaDataRoleAuthorizationStrategy.authorize(indicatorMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);

        return indicatorMenu;
    }



    protected NavbarDropDownButton newGisMenu() {

        // upload menu
        NavbarDropDownButton gisMenu = new NavbarDropDownButton(
                new StringResourceModel("navbar.gisMenu", this, null)) {
            private static final long serialVersionUID = 2L;

            @Override
            protected List<AbstractLink> newSubMenuButtons(final String arg0) {
                final List<AbstractLink> list = new ArrayList<>();


                list.add(new MenuBookmarkablePageLink<ListRegionIndicatorPage>(ListRegionIndicatorPage.class, null,
                        new StringResourceModel("navbar.regionIndicator", this, null))
                        .setIconType(FontAwesomeIconType.map_marker));

                list.add(new MenuBookmarkablePageLink<ListDepartmentIndicatorPage>(ListDepartmentIndicatorPage.class,
                        null, new StringResourceModel("navbar.departmentIndicator", this, null))
                        .setIconType(FontAwesomeIconType.map_marker));

                if (SecurityUtil.getCurrentAuthenticatedPerson() != null
                        && SecurityUtil.getCurrentAuthenticatedPerson().getRoles().stream()
                        .anyMatch(str -> str.getAuthority().equals(SecurityConstants.Roles.ROLE_ADMIN))) {
                    list.add(new MenuBookmarkablePageLink<>(EditGisSettingsPage.class,
                            new StringResourceModel("navbar.gisSettings", BasePage.this, null))
                            .setIconType(FontAwesomeIconType.map));
                }

                return list;
            }
        };

        gisMenu.setIconType(FontAwesomeIconType.upload);
        MetaDataRoleAuthorizationStrategy.authorize(gisMenu, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);

        MetaDataRoleAuthorizationStrategy.authorize(gisMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_FOCAL_POINT);

        return gisMenu;
    }

    /**
     * creates a new {@link Navbar} instance
     *
     * @param markupId The components markup id.
     * @return a new {@link Navbar} instance
     */
    protected Navbar newNavbar(final String markupId) {

        Navbar navbar = new Navbar(markupId);

        /**
         * Make sure to update the BaseStyles when the navbar position changes.
         *
         * @see org.devgateway.toolkit.forms.wicket.styles.BaseStyles
         */
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);

        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT, newHomeMenu(),
                newAdminMenu(), newCMMenu(), newDatasetMenu(), newIndicatorMenu(),
                newGisMenu(), newLanguageMenu(), newAccountMenu(), newLogoutMenu()));

        return navbar;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        // Load Styles.
        response.render(CssHeaderItem.forReference(BaseStyles.INSTANCE));
        response.render(CssHeaderItem.forReference(BootstrapCssReference.instance()));
        response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));

        // Load Scripts.
        response.render(RespondJavaScriptReference.headerItem());
        response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.getV2()));

        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(BaseStyles.class,
                "assets/js/fileupload.js")));

    }

    public boolean isAdmin() {
        return SecurityUtil.getCurrentAuthenticatedPerson().getRoles().stream()
                .anyMatch(str -> str.getAuthority().equals(SecurityConstants.Roles.ROLE_ADMIN));
    }
}
