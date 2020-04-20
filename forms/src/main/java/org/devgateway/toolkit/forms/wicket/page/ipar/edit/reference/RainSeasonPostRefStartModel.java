package org.devgateway.toolkit.forms.wicket.page.ipar.edit.reference;

import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.models.MonthDayModel;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;

import java.time.MonthDay;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonPostRefStartModel extends MonthDayModel {
    private static final long serialVersionUID = -5234449237172059312L;

    private IModel<RainSeasonPluviometricPostReferenceStart> parent;

    public RainSeasonPostRefStartModel(IModel<RainSeasonPluviometricPostReferenceStart> parent) {
        this.parent = parent;
    }

    @Override
    public MonthDay getObject() {
        return parent.getObject().getStartReference();
    }

    @Override
    public void setObject(MonthDay monthDay) {
        parent.getObject().setStartReference(monthDay);
    }
}
