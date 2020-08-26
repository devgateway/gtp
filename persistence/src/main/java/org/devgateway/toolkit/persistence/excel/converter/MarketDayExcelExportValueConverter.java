package org.devgateway.toolkit.persistence.excel.converter;

import static java.util.stream.Collectors.joining;

import org.devgateway.toolkit.persistence.service.MessageSourceService;
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Nadejda Mandrescu
 */
@Component
public class MarketDayExcelExportValueConverter implements ExcelExportValueConverter {

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public String convert(Object value) {
        Integer marketDays = (Integer) value;
        // TODO detect app locale, currently only french
        Locale locale = Locale.FRENCH;
        return marketDays.equals(MarketDaysUtil.ALL_DAYS) ? messageSourceService.getMessage("permanent", locale) :
                MarketDaysUtil.unpack(marketDays).stream()
                        .map(day -> day.getDisplayName(TextStyle.FULL, locale))
                        .collect(joining(", "));
    }
}
