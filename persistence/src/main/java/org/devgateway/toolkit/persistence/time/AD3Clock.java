package org.devgateway.toolkit.persistence.time;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Octavian Ciubotaru
 */
public final class AD3Clock {

    private static final Duration OFFSET = offset();

    private static final Clock CLOCK = Clock.offset(Clock.systemDefaultZone(), OFFSET);

    private AD3Clock() {
    }

    public static Clock systemDefaultZone() {
        return CLOCK;
    }

    public static boolean hasOffset() {
        return !OFFSET.isZero();
    }

    private static Duration offset() {
        try {
            String date = System.getenv("AD3_CLOCK_DATE");
            LocalDate newDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return Duration.between(LocalDate.now().atStartOfDay(), newDate.atStartOfDay());
        } catch (Exception e) {
            return Duration.ZERO;
        }
    }
}
