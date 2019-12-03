package org.devgateway.toolkit.persistence.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public final class MessageSource {

    public static final String FR_LOCALE = "fr";
    private static Map<Class, MessageSource> forClass = new HashMap<>();

    private static Map<String, MessageSource> withBasename = new HashMap<>();

    private final ReloadableResourceBundleMessageSource messageSource;

    private MessageSource(String... basenames) {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.addBasenames(basenames);
    }

    public static MessageSource forClass(Class<?> targetClass) {
        return forClass.computeIfAbsent(targetClass,
                c -> withBasename(c.getName().replace('.', '/')));
    }

    public static MessageSource withBasename(String basename) {
        return withBasename.computeIfAbsent(basename, MessageSource::new);
    }

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessage(String key, String locale) {
        if (StringUtils.isNotBlank(locale) && locale.equalsIgnoreCase(FR_LOCALE)) {
            return messageSource.getMessage(key, null, Locale.FRANCE);
        }
        return messageSource.getMessage(key, null, null);
    }
}
