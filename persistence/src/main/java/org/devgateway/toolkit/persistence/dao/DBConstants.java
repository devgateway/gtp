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
package org.devgateway.toolkit.persistence.dao;

import static java.time.Month.AUGUST;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MAY;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public final class DBConstants {

    private DBConstants() {

    }

    public static final int MAX_DEFAULT_TEXT_LENGTH = 32000;
    public static final int STD_DEFAULT_TEXT_LENGTH = 255;
    public static final int MAX_DEFAULT_TEXT_LENGTH_ONE_LINE = 3000;
    public static final int MAX_DEFAULT_TEXT_AREA = 10000;

    public static final int MIN_LATITUDE = 12;
    public static final int MAX_LATITUDE = 17;
    public static final int MIN_LONGITUDE = -18;
    public static final int MAX_LONGITUDE = -11;

    public static final List<Month> MONTHS = Arrays.asList(MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER);

    public static final int RAIN_SEASON_DECADAL_COUNT = MONTHS.size() * Decadal.values().length;
}
