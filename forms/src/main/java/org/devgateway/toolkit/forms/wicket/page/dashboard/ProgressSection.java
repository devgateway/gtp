package org.devgateway.toolkit.forms.wicket.page.dashboard;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.persistence.status.DatasetProgress;

import java.io.Serializable;

/**
 * @author Octavian Ciubotaru
 */
public abstract class ProgressSection<T extends DatasetProgress> implements Serializable {

    private final String role;

    public ProgressSection(String role) {
        this.role = role;
    }

    public abstract IModel<T> getDatasetProgress();

    public abstract Component createDetailedProgress(String id, IModel<T> model);

    public abstract Pair<Class<? extends Page>, PageParameters> getEditPage();

    public String getRole() {
        return role;
    }

    PageParameters forYear(IModel<Integer> yearModel) {
        PageParameters parameters = new PageParameters();
        parameters.set("year", yearModel.getObject());
        return parameters;
    }
}
