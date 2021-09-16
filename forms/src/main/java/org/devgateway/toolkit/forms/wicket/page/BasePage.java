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
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
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
import org.devgateway.toolkit.forms.wicket.page.dashboard.DataEntryDashboardPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.EditCNSCHeaderPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListOrganizationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.ListUserPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ReferenceDataPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.SystemCategoriesHomePage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.IndicatorHomePage;
import org.devgateway.toolkit.forms.wicket.page.user.EditUserPage;
import org.devgateway.toolkit.forms.wicket.page.user.LogoutPage;
import org.devgateway.toolkit.forms.wicket.styles.BaseStyles;
import org.devgateway.toolkit.persistence.dao.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

        HtmlTag html = new HtmlTag("html");
        html.add(new CssClassNameAppender(getClass().getSimpleName() + " "
                + StringUtils.trimToEmpty(getPageCssClassName())));
        add(html);

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

        if (SecurityUtil.getCurrentAuthenticatedPerson() == null) {
            ChangeLanguageLink changeLanguageLink = new ChangeLanguageLink(Locale.FRENCH);
            changeLanguageLink.onClick();
        }

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

        Label pageDescription = new Label("pageDescription", getPageDescriptionModel());
        add(pageDescription);
    }

    protected IModel<?> getPageDescriptionModel() {
        return new ResourceModel("page.description");
    }

    protected String getPageCssClassName() {
        return "";
    }

    protected NotificationPanel createFeedbackPanel() {
        final NotificationPanel notificationPanel = new NotificationPanel("feedback");
        notificationPanel.setOutputMarkupId(true);
        return notificationPanel;
    }

    public NavbarDropDownButton newLanguageMenu() {
        final NavbarDropDownButton languageDropDown =
                new NavbarDropDownButton(new StringResourceModel("navbar.lang", this, null)) {
                    private static final long serialVersionUID = 362699471337231179L;

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
        private static final long serialVersionUID = -3102043257980098434L;

        ChangeLanguageLink(Locale locale) {
            super(ButtonList.getButtonMarkupId(), Model.of(locale), Buttons.Type.Menu);
        }

        @Override
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
                SecurityConstants.Roles.ROLE_EDITOR);

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
                SecurityConstants.Roles.ROLE_EDITOR);
        return accountMenu;
    }

    protected NavbarButton<Homepage> newHomeMenu() {
        // home
        NavbarButton<Homepage> homeMenu = new NavbarButton<>(Homepage.class,
                new StringResourceModel("home", BasePage.this));
        homeMenu.setIconType(FontAwesomeIconType.home);
        MetaDataRoleAuthorizationStrategy.authorize(homeMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_EDITOR);
        return homeMenu;
    }

    protected NavbarButton<DataEntryDashboardPage> newDataEntryDashboardMenu() {
        NavbarButton<DataEntryDashboardPage> menu = new NavbarButton<>(DataEntryDashboardPage.class,
                new StringResourceModel("dataEntryDashboard", BasePage.this));
        menu.setIconType(FontAwesomeIconType.tachometer);
        MetaDataRoleAuthorizationStrategy.authorize(menu, Component.RENDER,
                SecurityConstants.Roles.ROLE_EDITOR);
        return menu;
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

                list.add(new MenuBookmarkablePageLink<>(EditCNSCHeaderPage.class,
                        new StringResourceModel("navbar.cnscMenu", BasePage.this, null))
                        .setIconType(FontAwesomeIconType.bars));

                /*
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
                */

                list.add(new MenuDivider());

                list.add(new MenuBookmarkablePageLink<>(FeatureManagerPage.class,
                        new StringResourceModel("navbar.featureManager", BasePage.this, null))
                        .setIconType(FontAwesomeIconType.lightbulb_o));

                list.add(new MenuBookmarkablePageLink<>(EditAdminSettingsPage.class,
                        new StringResourceModel("navbar.adminSettings", BasePage.this, null))
                        .setIconType(FontAwesomeIconType.briefcase));

                return list;
            }
        };

        adminMenu.setIconType(FontAwesomeIconType.cog);
        MetaDataRoleAuthorizationStrategy.authorize(adminMenu, Component.RENDER, SecurityConstants.Roles.ROLE_ADMIN);

        return adminMenu;
    }

    protected NavbarButton<?> newRefDataAndAlertsMenu() {
        final NavbarButton<?> categoriesMenu = new NavbarButton<>(ReferenceDataPage.class,
                new StringResourceModel("navbar.refData", this, null));
        categoriesMenu.setIconType(FontAwesomeIconType.list_ul);
        MetaDataRoleAuthorizationStrategy.authorize(categoriesMenu, Component.RENDER,
                SecurityConstants.Roles.ANY_REFERENCE_EDITOR_ROLES_STR);
        return categoriesMenu;
    }

    protected NavbarButton<?> newCategoriesMenu() {
        final NavbarButton<?> categoriesMenu = new NavbarButton<>(SystemCategoriesHomePage.class,
                new StringResourceModel("navbar.categories", this, null));
        categoriesMenu.setIconType(FontAwesomeIconType.list_ul);
        MetaDataRoleAuthorizationStrategy.authorize(categoriesMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_EDITOR);
        return categoriesMenu;
    }

    protected NavbarButton<Homepage> newIndicatorsMenu() {
        final NavbarButton<Homepage> indicatorsMenu = new NavbarButton<Homepage>(IndicatorHomePage.class,
                new StringResourceModel("navbar.indicators", this, null));
        indicatorsMenu.setIconType(FontAwesomeIconType.area_chart);
        MetaDataRoleAuthorizationStrategy.authorize(indicatorsMenu, Component.RENDER,
                SecurityConstants.Roles.ROLE_EDITOR);
        return indicatorsMenu;
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
                newDataEntryDashboardMenu(),
                newAdminMenu(), newRefDataAndAlertsMenu(), newCategoriesMenu(), newIndicatorsMenu(),
                newAccountMenu(), newLogoutMenu()));

        // navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT, newLanguageMenu()));

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
