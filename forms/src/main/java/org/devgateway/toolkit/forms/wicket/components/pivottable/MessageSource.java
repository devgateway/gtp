package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Octavian Ciubotaru
 */
public final class MessageSource {

    private final ReloadableResourceBundleMessageSource messageSource;

    private MessageSource(String... basenames) {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.addBasenames(basenames);
    }

    public static MessageSource forClass(Class<?> targetClass) {
        return withBasenames(targetClass.getName().replace('.', '/'));
    }

    public static MessageSource withBasenames(String... basenames) {
        return new MessageSource(basenames);
    }

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }
}
