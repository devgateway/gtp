package org.devgateway.toolkit.persistence.excel.service;

import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.devgateway.toolkit.persistence.service.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author Nadejda Mandrescu
 */
@Service
public class TranslateServiceImpl implements TranslateService {

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public String getTranslation(Class clazz, Field field) {
        return messageSourceService.getMessage(field.getAnnotation(ExcelExport.class).name());
    }
}
