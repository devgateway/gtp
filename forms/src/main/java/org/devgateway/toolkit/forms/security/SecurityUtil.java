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
package org.devgateway.toolkit.forms.security;

import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Role;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.spring.CustomJPAUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.security.Principal;

public final class SecurityUtil {

    private SecurityUtil() {

    }

    /**
     * returns the principal object. In our case the principal should be
     * {@link Person}
     *
     * @return the principal or null
     * @see Principal
     */
    public static Person getCurrentAuthenticatedPerson() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        final Object principal = authentication.getPrincipal();
        if (principal instanceof Person) {
            return (Person) principal;
        }
        return null;
    }

    /**
     * Returns true if the user has ROLE_ADMIN
     *
     * @param p
     * @return
     */
    public static boolean isUserAdmin(final Person p) {
        if (p == null || p.getRoles() == null) {
            return false;
        }
        for (final Role r : p.getRoles()) {
            if (r.getAuthority().equalsIgnoreCase(SecurityConstants.Roles.ROLE_ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCurrentUserAdmin() {
        final Person p = getCurrentAuthenticatedPerson();
        return isUserAdmin(p);
    }

    /**
     * If changed user is the currently authenticated user, then update the Authentication with the latest
     * Person object.
     *
     * @param person person that changed
     */
    public static void onPersonChanged(Person person) {
        if (person.equals(getCurrentAuthenticatedPerson())) {
            person.setAuthorities(CustomJPAUserDetailsService.getGrantedAuthorities(person));

            SecurityContextHolder.getContext().setAuthentication(
                    new PreAuthenticatedAuthenticationToken(person, null, person.getAuthorities()));
        }
    }

    public static boolean canCurrentUserAccess(IndicatorMetadata indicatorMetadata) {
        final Person p = getCurrentAuthenticatedPerson();
        return isUserAdmin(p) || p.getOrganization().equals(indicatorMetadata.getOrganization());
    }
}
