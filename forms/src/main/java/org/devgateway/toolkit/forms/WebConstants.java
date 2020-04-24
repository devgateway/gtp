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

import static org.devgateway.toolkit.persistence.dao.DBConstants.MAX_LATITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MAX_LONGITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MIN_LATITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MIN_LONGITUDE;

import com.google.common.collect.ImmutableSet;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.devgateway.toolkit.persistence.dao.DBConstants;

import java.util.Locale;
import java.util.Set;

public final class WebConstants {

    private WebConstants() {

    }

    public static final int PAGE_SIZE = 10;
    public static final int SELECT_PAGE_SIZE = 25;
    public static final int NO_PAGE_SIZE = 999;

    public static final String PARAM_VIEW_MODE = "viewMode";

    public static final String PARAM_ID = "id";
    public static final String PARAM_REVISION_ID = "revisionId";
    public static final String PARAM_ENTITY_CLASS = "class";

    public static final class StringValidators {
        public static final StringValidator MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT =
                StringValidator.maximumLength(DBConstants.STD_DEFAULT_TEXT_LENGTH);
        public static final StringValidator MAXIMUM_LENGTH_VALIDATOR_ONE_LINE_TEXT =
                StringValidator.maximumLength(DBConstants.MAX_DEFAULT_TEXT_LENGTH_ONE_LINE);
        public static final StringValidator MAXIMUM_LENGTH_VALIDATOR_ONE_LINE_TEXTAREA =
                StringValidator.maximumLength(DBConstants.MAX_DEFAULT_TEXT_AREA);
    }

    public static final class Validators {
        public static final RangeValidator<Double> LATITUDE_RANGE =
                RangeValidator.range((double) MIN_LATITUDE, (double) MAX_LATITUDE);
        public static final RangeValidator<Double> LONGITUDE_RANGE =
                RangeValidator.range((double) MIN_LONGITUDE, (double) MAX_LONGITUDE);
    }

    // add more languages here. It is pointless to make this dynamic because the
    // wicket i18n is in .properties files so we need
    // to change the src code anyway.
    public static final Set<Locale> AVAILABLE_LOCALES = ImmutableSet.of(Locale.ENGLISH, Locale.FRENCH);

    public static final String DISABLE_FORM_LEAVING_JS
            = "if(typeof disableFormLeavingConfirmation === 'function') disableFormLeavingConfirmation();";
    public static final String V_POSITION = "vPosition";
    public static final String MAX_HEIGHT = "maxPosition";

}
