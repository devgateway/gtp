package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;

/**
 * @author Nadejda Mandrescu
 */
public class PluviometricPostDayModel implements IModel<Double> {
    private static final long serialVersionUID = 5524323971330762504L;

    private final IModel<PluviometricPostRainfall> postRainfallModel;
    private final int day;

    public PluviometricPostDayModel(IModel<PluviometricPostRainfall> postRainfallModel, int day) {
        this.postRainfallModel = postRainfallModel;
        this.day = day;
    }

    @Override
    public Double getObject() {
        return postRainfallModel.getObject().getRainfalls().stream()
                .filter(r -> r.getDay() == day)
                .findFirst()
                .map(Rainfall::getRain)
                .orElse(null);
    }

    @Override
    public void setObject(Double rain) {
        postRainfallModel.getObject().getRainfalls().stream()
                .filter(r -> r.getDay() == day)
                .findFirst()
                .orElseGet(() -> {
                    Rainfall r = new Rainfall(day);
                    postRainfallModel.getObject().addRainfall(r);
                    return r;
                })
                .setRain(rain);
    }
}
