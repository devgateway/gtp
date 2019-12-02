package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dto.MessageSource;
import org.devgateway.toolkit.persistence.excel.service.TranslateService;

import java.lang.reflect.Field;

public class IndicatorTranslateService implements TranslateService {


    private String lang;

    public IndicatorTranslateService(final String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String getTranslation(Class clazz, Field field) {
        MessageSource source = MessageSource.forClass(clazz);
        return source.getMessage(field.getName(), lang);
    }
}
