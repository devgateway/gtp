package org.devgateway.toolkit.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class MessageSourceService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(final String code) {
        return getMessage(code, Locale.getDefault());
    }

    public String getMessage(final String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(final String code, final Object... params) {
        return messageSource.getMessage(code, params, Locale.getDefault());
    }

}
