package org.devgateway.toolkit.persistence.dao.categories;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.util.Constants;

import javax.persistence.Entity;

/**
 * @author dbianco
 */
@Entity
public class ContentType extends Category  {

    public String getNameEnFr() {
        String frLabel = getLocalizedLabel(Constants.LANG_FR);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(label)) {
            sb.append(label);
            if (StringUtils.isNotBlank(frLabel)) {
                sb.append(" / ");
            }
        }
        if (StringUtils.isNotBlank(frLabel)) {
            sb.append(frLabel);
        }
        return sb.toString();
    }
}
