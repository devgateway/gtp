package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.PivotTableField;
import org.devgateway.toolkit.persistence.repository.MarketRepository;
import org.devgateway.toolkit.persistence.repository.category.CropTypeRepository;

/**
 * Wicket component that acts as a wrapper for pivottable.js library.
 *
 * @author Octavian Ciubotaru
 */
public class PivotTable extends GenericPanel<Class<?>> {

    private final WebMarkupContainer table;

    private static final List<String> AGGREGATOR_NAMES = ImmutableList.of("average", "count", "sum");

    private static final List<String> RENDERER_NAMES = ImmutableList.of("table", "heatmap", "rowHeatmap", "colHeatmap");

    @SpringBean
    private ObjectMapper objectMapper;

    @SpringBean
    private MarketRepository marketRepository;

    @SpringBean
    private CropTypeRepository cropTypeRepository;

    public PivotTable(String id, Model<Class<?>> model) {
        super(id, model);

        table = new WebMarkupContainer("table");
        table.setOutputMarkupId(true);
        add(table);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(PivotTableJavaScriptResourceReference.get()));
        response.render(PivotCSSResourceReference.getHeaderItem(getApplication()));
        response.render(OnLoadHeaderItem.forScript(getInitScript()));
    }

    private String getInitScript() {
        Class<?> datasetClass = getModelObject();

        MessageSource datasetMessages = MessageSource.forClass(datasetClass);
        MessageSource pivotMessages = MessageSource.withBasenames("pivot");

        List<PivotField> fields = FieldUtils.getAllFieldsList(datasetClass).stream()
                .filter(f -> f.isAnnotationPresent(PivotTableField.class))
                .map(this::toPivotField)
                .collect(toList());

        DatasetAnalysisConfigurer mp = new MarketPriceDatasetAnalysisConfigurer(marketRepository, cropTypeRepository);

        List<PivotField> extraFields = mp.getExtraFields();

        List<PivotField> allFields = new ArrayList<>();
        allFields.addAll(fields);
        allFields.addAll(extraFields);

        Map<String, String> fieldLabels = allFields.stream()
                .collect(toMap(PivotField::getName, pf -> datasetMessages.getMessage(pf.getName(), getLocale())));

        Locale locale = WebSession.get().getLocale();

        Function<PivotField, String> toFieldLabel = pf -> fieldLabels.get(pf.getName());

        PivotUIOpts pivotUIOpts = new PivotUIOpts(
                mp.getRows().stream().map(fieldLabels::get).collect(toList()),
                mp.getCols().stream().map(fieldLabels::get).collect(toList()),
                mp.getVals().stream().map(fieldLabels::get).collect(toList()),
                allFields.stream().filter(PivotField::isHideInAggregators).map(toFieldLabel).collect(toList()),
                allFields.stream().filter(PivotField::isHideInDragAndDrop).map(toFieldLabel).collect(toList()),
                pivotMessages.getMessage(mp.getAggregatorName(), locale),
                pivotMessages.getMessage("table", locale));

        PivotTableOpts pivotTableOpts = new PivotTableOpts();

        pivotTableOpts.setElementId(table.getMarkupId());
        pivotTableOpts.setDataset(datasetClass.getSimpleName());
        pivotTableOpts.setDataUrl(getDataUrl());
        pivotTableOpts.setFields(fields.stream().collect(toMap(PivotField::getName, toFieldLabel)));
        pivotTableOpts.setExtraFields(extraFields.stream().collect(toMap(PivotField::getName, toFieldLabel)));
        pivotTableOpts.setPivotUIOpts(pivotUIOpts);
        pivotTableOpts.setLanguage(locale.getLanguage());

        pivotTableOpts.setDayNames(IntStream.rangeClosed(1, 7)
                        .mapToObj(m -> pivotMessages.getMessage(String.format("day.%d", m), locale))
                        .collect(toList()));
        pivotTableOpts.setMthNames(IntStream.rangeClosed(1, 12)
                .mapToObj(m -> pivotMessages.getMessage(String.format("month.%d", m), locale))
                .collect(toList()));

        pivotTableOpts.setAggregatorNames(AGGREGATOR_NAMES.stream()
                .map(agg -> pivotMessages.getMessage(agg, locale))
                .collect(toList()));

        pivotTableOpts.setRendererNames(RENDERER_NAMES.stream()
                .map(agg -> pivotMessages.getMessage(agg, locale))
                .collect(toList()));

        return String.format("PivotTable.init(%s, %s)", toJson(pivotTableOpts),
                toJson(mp.getExtraOpts(locale.getLanguage())));
    }

    /**
     * This method makes an assumption that {@link org.springframework.web.servlet.DispatcherServlet}
     * and {@link WicketFilter} are mounted on the same path.
     */
    private String getDataUrl() {
        String url = String.format("/%s/dump", StringUtils.uncapitalize(getModelObject().getSimpleName()));
        return RequestCycle.get().getUrlRenderer().renderContextRelativeUrl(url);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to json.", e);
        }
    }

    private PivotField toPivotField(Field field) {
        String name = field.getName();
        PivotTableField pvt = field.getAnnotation(PivotTableField.class);
        return new PivotField(name, pvt.hideInAggregators(), pvt.hideInDragAndDrop());
    }
}
