package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nadejda Mandrescu
 */
public class PluviometricPostRainfallModel implements IModel<PluviometricPostRainfall> {
    private static final long serialVersionUID = -7718022014894374705L;

    private IModel<PluviometricPostRainfall> pluviometricPostRainfallModel;

    private List<IModel<Rainfall>> rainfallListModel = Stream.generate(() -> (IModel<Rainfall>) null)
            .limit(32).collect(Collectors.toList());

    public PluviometricPostRainfallModel(IModel<PluviometricPostRainfall> pluviometricPostRainfallModel) {
        this.pluviometricPostRainfallModel = pluviometricPostRainfallModel;
        pluviometricPostRainfallModel.getObject().getRainfalls().forEach(this::addRainfall);
    }

    public void addRainfall(Rainfall rainfall) {
        rainfallListModel.set(rainfall.getDay(), Model.of(rainfall));
    }

    public Double getRain(Integer day) {
        IModel<Rainfall> rainfallModel = rainfallListModel.get(day);
        return rainfallModel == null ? null : rainfallModel.getObject().getRain();
    }

    public void setRain(Integer day, Double rain) {
        IModel<Rainfall> rainfallModel = rainfallListModel.get(day);
        if (rainfallModel == null) {
            Rainfall rainfall = new Rainfall();
            rainfall.setPluviometricPostRainfall(pluviometricPostRainfallModel.getObject());
            rainfall.setDay(day);
            rainfallModel = Model.of(rainfall);
            rainfallListModel.set(day, rainfallModel);
        }
        rainfallModel.getObject().setRain(rain);
    }

    @Override
    public PluviometricPostRainfall getObject() {
        return pluviometricPostRainfallModel.getObject();
    }
}
