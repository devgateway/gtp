package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfallMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.MaxFileSizeValidator;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap.ListDecadalRainfallMapPage;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayer;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;
import org.devgateway.toolkit.persistence.service.indicator.rainfallMap.DecadalRainfallMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.wicketstuff.annotation.mount.MountPath;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath("/rainfall-map")
public class EditDecadalRainfallMapPage extends AbstractEditPage<DecadalRainfallMap> {
    private static final long serialVersionUID = -4145077002056116408L;

    private static final Logger logger = LoggerFactory.getLogger(EditDecadalRainfallMapPage.class);

    private static final Integer MAX_ZLEVEL_GRADES = 30;

    @SpringBean
    private DecadalRainfallMapService decadalRainfallMapService;

    public EditDecadalRainfallMapPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = decadalRainfallMapService;
        setListPage(ListDecadalRainfallMapPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        editForm.add(new Label("layersInfo", new StringResourceModel("layersInfo")).setEscapeModelStrings(false));

        DecadalRainfallMap decadalMap = editForm.getModelObject();
        for (RainfallMapLayerType type : RainfallMapLayerType.values()) {
            addLayer(decadalMap.computeIfAbsent(type));
        }

        deleteButton.setVisible(false);
    }

    private void addLayer(RainfallMapLayer layer) {
        FileInputBootstrapFormComponent file = new FileInputBootstrapFormComponent(
                layer.getType().name(), new PropertyModel<>(layer, "file"));
        file.maxFiles(1);
        file.allowedFileExtensions("json");
        file.getField().add(new MaxFileSizeValidator(RainfallMapLayer.MAX_FILE_SIZE, file.getField()));
        file.getField().add(new LayerContentValidator());
        file.required();
        editForm.add(file);
    }

    private class LayerContentValidator implements IValidator<Collection<FileMetadata>> {
        private static final long serialVersionUID = 6752552786127843282L;


        @Override
        public void validate(IValidatable<Collection<FileMetadata>> validatable) {
            validatable.getValue().forEach(fileMetadata -> {
                String errKey = validate(fileMetadata);
                if (errKey != null) {
                    ValidationError error = new ValidationError(this, errKey);
                    error.setVariable("maxGrades", MAX_ZLEVEL_GRADES);
                    validatable.error(error);
                }
            });
        }

        private String validate(FileMetadata fileMetadata) {
            try {
                String jsonStr = new String(fileMetadata.getContent().getBytes(), StandardCharsets.UTF_8);
                Map<String, Object> json = new GsonJsonParser().parseMap(jsonStr);

                String type = (String) json.get("type");
                if (!type.equals("FeatureCollection")) {
                    return "invalidDataFormat";
                }

                Set<Double> zlevels = new HashSet<>();
                Set<Double> zlevelsNoCoordinates = new HashSet<>();
                List<Map<String, Object>> zlFeatures = ((List<Map<String, Object>>) json.get("features"))
                        .stream().filter(f -> {
                            if (!f.containsKey("properties")) {
                                return false;
                            }
                            Map<String, Object> props = (Map<String, Object>) f.get("properties");
                            Object zlevel = props.get("ZLEVEL");
                            if (zlevel == null) {
                                return false;
                            } else {
                                Double zl = Double.valueOf(zlevel.toString());
                                zlevels.add(zl);
                                Map<String, Object> geometry = (Map) f.getOrDefault("geometry", Collections.emptyMap());
                                List coordinates = (List) geometry.get("coordinates");
                                if (coordinates.isEmpty()) {
                                    zlevelsNoCoordinates.add(zl);
                                }
                            }
                            return true;
                        }).collect(Collectors.toList());

                if (zlFeatures.isEmpty()) {
                    return "noZLevels";
                }
                zlFeatures.remove(0);
                if (zlevels.size() > MAX_ZLEVEL_GRADES) {
                    return "tooManyZLevels";
                } else if (Collections.min(zlevels) < 0) {
                    return "negativeZLevels";
                } else if (!zlevelsNoCoordinates.isEmpty()) {
                    return "noCoordinates";
                }

                // store only features with ZLEVEL
                json.put("features", zlFeatures);
                fileMetadata.getContent().setBytes(jsonToBytes(json));

            } catch (Exception e) {
                logger.error("Rainfall Map layer validation error:", e);
                return "invalidDataFormat";
            }
            return null;
        }
    }

    private byte[] jsonToBytes(Map<String, Object> json) {
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        String gsonString = gson.toJson(json, gsonType);
        return gsonString.getBytes();
    }
}
