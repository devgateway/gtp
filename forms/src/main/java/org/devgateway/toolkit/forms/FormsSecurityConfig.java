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
package org.devgateway.toolkit.forms;

import org.devgateway.toolkit.web.spring.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1) // this ensures the forms security comes first
public class FormsSecurityConfig extends WebSecurityConfig {

    /**
     * We ensure the superclass configuration is being applied Take note the
     * {@link FormsSecurityConfig} extends {@link WebSecurityConfig} which has
     * configuration for the dg-toolkit/web module. We then apply ant matchers
     * and ignore security for css/js/images resources, and wicket mounted
     * resources
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        super.configure(web);

        web.ignoring().antMatchers("/img/**", "/css*/**", "/js*/**", "/assets*/**",
                "/favicon.ico", "/resources/**", "/resources/public/**");

        // resources imported through wicket are public
        web.ignoring().antMatchers(
                "/admin/wicket/resource/**/*.js",
                "/admin/wicket/resource/**/*.css",
                "/admin/wicket/resource/**/*.woff",
                "/admin/wicket/resource/**/*.woff2",
                "/admin/wicket/resource/**/*.ttf",
                "/admin/wicket/resource/**/*.png",
                "/admin/wicket/resource/**/*.jpg",
                "/admin/wicket/resource/**/*.gif");

        // forgot pwd page is public as well
        web.ignoring().antMatchers("/admin/forgotPassword");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);

        // we do not allow anyonymous token. When
        // enabled this basically means any guest
        // user will have an annoymous default role
        http.anonymous().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).
                // we let Wicket create and manage sessions, so we disable
                // session creation by spring
                        and().csrf().disable(); // csrf protection interferes with some
        // wicket stuff

        // we enable http rememberMe cookie for autologin
        // http.rememberMe().key(UNIQUE_SECRET_REMEMBER_ME_KEY);

        // resolved the error Refused to display * in a frame because it set
        // 'X-Frame-Options' to 'DENY'.
        http.headers().contentTypeOptions().and().xssProtection().and().cacheControl().and()
                .httpStrictTransportSecurity().and().frameOptions().sameOrigin();
        // allow embedding in frame
        http.antMatcher("/index.html")
                .headers().frameOptions().disable()
                .and().headers().contentSecurityPolicy(
                "frame-ancestors 'self' http://localhost:* http://*.anacim.sn https://*.anacim.sn filesystem:");
    }
}
