package org.devgateway.toolkit.forms.wicket.components.table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Nadejda Mandrescu
 */
public class BookmarkableResettingFilterForm<T extends JpaFilterState> extends ResettingFilterForm<T> {
    private static final long serialVersionUID = 5194609629900464242L;

    private static final Logger logger = LoggerFactory.getLogger(BookmarkableResettingFilterForm.class);

    private final Class<? extends AbstractListPage> responseClass;

    public BookmarkableResettingFilterForm(final String id, final IFilterStateLocator<T> locator,
            final DataTable<?, ?> dataTable, final Class<? extends AbstractListPage> responseClass,
            final PageParameters pageParameters) {
        this(id, locator, dataTable, responseClass);
        locator.setFilterState(configureRequest(pageParameters, locator.getFilterState()));
    }

    public BookmarkableResettingFilterForm(final String id, final IFilterStateLocator<T> locator,
            final DataTable<?, ?> dataTable, final Class<? extends AbstractListPage> responseClass) {
        super(id, locator, dataTable);
        this.responseClass = responseClass;
    }

    public static <Y> Y configureRequest(final PageParameters pageParameters, Y state) {
        JSONObject jsonState = getFilterStateAsJson(state);
        pageParameters.getNamedKeys().forEach(key -> {
            StringValue stringValue = pageParameters.get(key);
            if (!stringValue.isNull() && !stringValue.isEmpty()) {
                Object value = stringValue;
                if (jsonState.get(key) instanceof JSONArray) {
                    value = new JSONArray(stringValue.toString());
                }
                jsonState.put(key, value);
            }
        });
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonState.toString(), (Class<Y>) state.getClass());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return state;
        }
    }

    private void configureResponse() {
        PageParameters pageParameters = new PageParameters();
        JSONObject jsonState = getFilterStateAsJson();
        jsonState.keySet().forEach(key -> {
            Object value = jsonState.get(key);
            if (!jsonState.isNull(key)) {
                pageParameters.set(key, value);
            }
        });
        setResponsePage(responseClass, pageParameters);
    }

    private JSONObject getFilterStateAsJson() {
        return getFilterStateAsJson(getStateLocator().getFilterState());
    }

    private static JSONObject getFilterStateAsJson(Object obj) {
        try {
            ObjectMapper om = new ObjectMapper();
            String strVal = om.writeValueAsString(obj);
            return new JSONObject(strVal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert filter state to JSONObject.", e);
        }
    }

    @Override
    protected void onModelChanged() {
        super.onModelChanged();
        configureResponse();
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        configureResponse();
    }

}
