package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.model.IModel;

/**
 * @author Nadejda Mandrescu
 */
public class PluviometricPostDayModel implements IModel<Double> {
    private static final long serialVersionUID = 5524323971330762504L;

    private PluviometricPostRainfallModel pluviometricPostRainfallModel;
    private Integer day;

    public PluviometricPostDayModel(PluviometricPostRainfallModel pluviometricPostRainfallModel, Integer day) {
        this.pluviometricPostRainfallModel = pluviometricPostRainfallModel;
        this.day = day;
    }

    @Override
    public Double getObject() {
        return pluviometricPostRainfallModel.getRain(day);
    }

    @Override
    public void setObject(Double rain) {
        pluviometricPostRainfallModel.setRain(day, rain);
    }
}
