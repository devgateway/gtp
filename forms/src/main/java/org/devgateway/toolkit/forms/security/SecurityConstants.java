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
/**
 *
 */
package org.devgateway.toolkit.forms.security;

import java.util.Arrays;
import java.util.List;

/**
 * @author mpostelnicu
 *
 */
public final class SecurityConstants {

    public static final class Roles {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_EDITOR = "ROLE_EDITOR";
        public static final String ROLE_RAINFALL_EDITOR = "ROLE_RAINFALL_EDITOR";
        public static final String ROLE_RAINFALL_SEASON_EDITOR = "ROLE_RAINFALL_SEASON_EDITOR";
        public static final String ROLE_RIVER_LEVEL_EDITOR = "ROLE_RIVER_LEVEL_EDITOR";
        public static final String ROLE_MARKET_EDITOR = "ROLE_MARKET_EDITOR";
        public static final String ROLE_GTP_BULLETIN_EDITOR = "ROLE_GTP_BULLETIN_EDITOR";
        public static final String ROLE_DISEASE_SITUATION_EDITOR = "ROLE_DISEASE_SITUATION_EDITOR";

        public static final List<String> ANY_REFERENCE_EDITOR_ROLES = Arrays.asList(
                ROLE_RAINFALL_EDITOR,
                ROLE_RAINFALL_SEASON_EDITOR,
                ROLE_RIVER_LEVEL_EDITOR
        );
        public static final String ANY_REFERENCE_EDITOR_ROLES_STR =
                String.join(",", ANY_REFERENCE_EDITOR_ROLES.toArray(new String[] {}));
    }
}
