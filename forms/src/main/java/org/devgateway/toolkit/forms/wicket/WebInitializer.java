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
package org.devgateway.toolkit.forms.wicket;

import java.util.Collections;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * This class is the replacement of the web.xml. It registers the wicket filter
 * in the spring aware configuration style.
 *
 * @author Stefan Kloe
 *
 */
@Configuration
public class WebInitializer implements ServletContextInitializer {

    private static final String PARAM_APP_BEAN = "applicationBean";
    public static final String ADMIN_PATH = "/admin/*";

    @Override
    public void onStartup(final ServletContext sc) throws ServletException {

        sc.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));

        // AUTO configured by spring boot 1.2.x and upper
        sc.addFilter("Spring OpenEntityManagerInViewFilter",
                org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter.class)
                .addMappingForUrlPatterns(null, false, ADMIN_PATH);

        FilterRegistration filter = sc.addFilter("wicket-filter", WicketFilter.class);
        filter.setInitParameter(WicketFilter.APP_FACT_PARAM, SpringWebApplicationFactory.class.getName());
        filter.setInitParameter(PARAM_APP_BEAN, "formsWebApplication");
        // This line is the only surprise when comparing to the equivalent
        // web.xml. Without some initialization seems to be missing.
        filter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, ADMIN_PATH);
        filter.addMappingForUrlPatterns(null, false, ADMIN_PATH);

        sc.addListener(new HttpSessionEventPublisher());

    }

}
