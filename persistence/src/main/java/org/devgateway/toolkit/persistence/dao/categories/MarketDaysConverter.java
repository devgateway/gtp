package org.devgateway.toolkit.persistence.dao.categories;

import java.time.DayOfWeek;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * @author Octavian Ciubotaru
 */
public class MarketDaysConverter extends StdConverter<Integer, String> {

    @Override
    public String convert(Integer value) {
        char[] ch = new char[DayOfWeek.values().length];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = ((value >> i) & 1) == 0 ? '0' : '1';
        }
        return new String(ch);
    }
}
