package org.devgateway.toolkit.persistence.dto.ipar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

/**
 * @author Octavian Ciubotaru
 */
public final class MessageSource {

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
        try {
            if (StringUtils.isNotBlank(locale) && locale.equalsIgnoreCase(LANG_FR)) {
                return messageSource.getMessage(key, null, Locale.FRANCE);
            }
            return messageSource.getMessage(key, null, null);
        } catch (Exception noMessage) {
            return key;
        }
    }
}