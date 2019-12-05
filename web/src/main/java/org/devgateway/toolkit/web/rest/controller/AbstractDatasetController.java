package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dto.DataDTO;
import org.devgateway.toolkit.persistence.dto.MessageSource;
import org.devgateway.toolkit.persistence.service.AbstractDatasetService;
import org.devgateway.toolkit.web.rest.controller.filter.DefaultFilterPagingRequest;
import org.devgateway.toolkit.web.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Octavian Ciubotaru
 */

public abstract class AbstractDatasetController<T extends AbstractAuditableEntity & Serializable,
        S extends DefaultFilterPagingRequest, R extends DataDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatasetController.class);
    public static final String MIN = "min";
    public static final String MAX = "max";

    protected AbstractDatasetService<T> datasetService;

    AbstractDatasetController(AbstractDatasetService<T> datasetService) {
        this.datasetService = datasetService;
    }

    protected abstract Specification<T> getSpecifications(S request);
    protected abstract R getDTO(T indicator, String lang);


    @CrossOrigin
    @ApiOperation(value = "Get validated data paginated")
    @RequestMapping(value = "/all", method = POST)
    public @ResponseBody Page<T> getPaginated(@RequestBody(required = false) @Valid final S pageRequest) {
        LOGGER.debug("get all validated data");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.Direction.ASC, "id");
        return datasetService.findAll(getSpecifications(pageRequest), pageable);
    }

    @CrossOrigin
    @ApiOperation(value = "Dump validated data")
    @RequestMapping(value = "/dump", method = POST)
    public @ResponseBody List<T> getAllValidated(@RequestBody(required = false) @Valid final S pageRequest) {

        return findBySpec(pageRequest);
    }

    protected List<T> findBySpec(final S req) {
        return datasetService.findAll(getSpecifications(req));
    }

    @CrossOrigin
    @ApiOperation(value = "Dump all data, even not validated")
    @RequestMapping(value = "/unchecked", method = POST)
    public @ResponseBody List<T> getAllData() {
        return datasetService.findAll();
    }


    @CrossOrigin
    @ApiOperation(value = "Get csv data")
    @RequestMapping(value = "/summary/csv", method = POST, produces = "application/json")
    public void getSummaryIndicatorCSVPost(@ModelAttribute @Valid final S req,
            final HttpServletResponse response) {
        getSummaryWrapper(req, response);
    }


    @CrossOrigin
    @ApiOperation(value = "Get csv data")
    @RequestMapping(value = "/summary/csv", method = GET, produces = "application/json")
    public void getSummaryIndicatorCSVGet(@ModelAttribute @Valid final S req,
                                          final HttpServletResponse response) {
        getSummaryWrapper(req, response);
    }

    private void getSummaryWrapper(S req, HttpServletResponse response) {
        String lang = req != null ? req.getLang() : null;
        List<T> indicators = findBySpec(req);
        List<R> dataList = indicators.stream().map(p -> getDTO(p, lang)).collect(Collectors.toList());
        createCSVResponse(dataList, lang, response);
    }

    /*private ResponseEntity.BodyBuilder getBodyBuilder(WebRequest webRequest) {
        String eTag = datasetService.getETagForDump();

        if (eTag != null && webRequest.checkNotModified(eTag)) {
            return null;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        if (eTag != null) {
            responseBuilder.eTag(eTag);
        }
        return responseBuilder;
    }*/

    protected void createCSVResponse(final List data, final String lang, final HttpServletResponse response) {
        try {
            Gson gson;
            if (data != null && data.size() > 0) {
                gson = new GsonBuilder()
                        .setFieldNamingStrategy(new FieldNamingStrategy() {
                            @Override
                            public String translateName(Field field) {
                                MessageSource source = MessageSource.forClass(data.get(0).getClass());
                                return source.getMessage(field.getName(), lang);
                            }
                        })
                        .setExclusionStrategies(new AnnotationExclusionStrategy())
                        .create();
            } else {
                gson = new Gson();
            }
            List<Map<String, String>> csvObj = JSONUtil.parseJson(gson.toJson(data));

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");
            response.setCharacterEncoding("Cp1252");

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(JSONUtil.getCSV(csvObj).getBytes("Cp1252"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        }
    }

    class AnnotationExclusionStrategy implements ExclusionStrategy {
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(JsonIgnore.class) != null;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(JsonIgnore.class) != null;
        }
    }
}
