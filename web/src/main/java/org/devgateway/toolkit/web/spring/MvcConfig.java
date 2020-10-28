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
package org.devgateway.toolkit.web.spring;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.jackson.DecadalDeserializer;
import org.devgateway.toolkit.persistence.jackson.DecadalSerializer;
import org.devgateway.toolkit.persistence.jackson.MonthAsIntDeserializer;
import org.devgateway.toolkit.persistence.jackson.MonthAsIntSerializer;
import org.devgateway.toolkit.web.generators.GenericExcelKeyGenerator;
import org.devgateway.toolkit.web.generators.GenericKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.time.Month;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Bean(name = "genericExcelKeyGenerator")
    public KeyGenerator genericExcelKeyGenerator(final ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return new GenericExcelKeyGenerator(objectMapper);
    }

    @Bean(name = "genericKeyGenerator")
    public KeyGenerator genericKeyGenerator(final ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new GenericKeyGenerator(objectMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Module customTypes() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new DecadalSerializer());
        module.addDeserializer(Decadal.class, new DecadalDeserializer());
        module.addSerializer(new MonthAsIntSerializer());
        module.addDeserializer(Month.class, new MonthAsIntDeserializer());
        return module;
    }
}
