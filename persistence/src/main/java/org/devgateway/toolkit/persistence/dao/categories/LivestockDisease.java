package org.devgateway.toolkit.persistence.dao.categories;

import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
public class LivestockDisease extends Category {
    private static final long serialVersionUID = 8355230825811512846L;

    @ExcelExport(name = "disease", useTranslation = true)
    private transient String diseaseLabel;

    public String getDiseaseLabel() {
        return label;
    }
}
