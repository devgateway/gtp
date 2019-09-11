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
package org.devgateway.toolkit.persistence.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniel Oliva
 */
public final class ImportUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImportUtils.class);

    private static final String COMMA_VALUE = ",";
    private static final String DOT_VALUE = ".";
    private static final String TRAILING_ZERO = ".0";
    private static final SimpleDateFormat DF1 = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DF2 = new SimpleDateFormat("dd-MM-yyyy");

    private ImportUtils() {
    }

    public static Long getLongFromCell(final Cell cell) {
        Long ret = null;
        try {
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                ret = (long) cell.getNumericCellValue();
            } else if (cell != null && cell.getCellType() == CellType.STRING) {
                ret = Long.valueOf(cell.getStringCellValue().trim());
            }
        } catch (Exception e) {
            logger.error("error: " + e);
        }
        return ret;
    }

    public static Double getDoubleFromCell(final Cell cell) {
        Double ret = null;
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            ret = cell.getNumericCellValue();
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            ret = Double.valueOf(cell.getStringCellValue().replace(COMMA_VALUE, DOT_VALUE));
        }
        return ret;
    }

    public static String getStringFromCell(final Cell cell) {
        String ret = null;
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            String value = String.valueOf(cell.getNumericCellValue());
            ret = value.endsWith(TRAILING_ZERO) ? value.replace(TRAILING_ZERO, "") : value;
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            ret = cell.getStringCellValue();
        }
        return ret;
    }

    public static boolean getBooleanFromCell(final Cell cell) {
        boolean ret = false;
        if (cell != null && cell.getCellType() == CellType.BOOLEAN) {
            ret = cell.getBooleanCellValue();
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            ret = Boolean.valueOf(cell.getStringCellValue());
        }
        return ret;
    }

    public static String getStringFromCell(final Cell cell, final String defaultValue) {
        String ret = getStringFromCell(cell);
        return ret != null ? ret : defaultValue;
    }

    public static Date getDateFromCell(final Cell cell) {
        Date ret = null;
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            ret = cell.getDateCellValue();
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            try {
                ret = DF1.parse(cell.getStringCellValue());
            } catch (ParseException e) {
                logger.debug("Parsing1 exception ", e.getCause());
            }
            if (ret == null) {
                try {
                    ret = DF2.parse(cell.getStringCellValue());
                } catch (ParseException e) {
                    logger.debug("Parsing2 exception ", e.getCause());
                }
            }
        }
        return ret;
    }
}
