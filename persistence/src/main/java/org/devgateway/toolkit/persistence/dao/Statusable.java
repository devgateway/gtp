package org.devgateway.toolkit.persistence.dao;

/**
 * @author mihai
 *         <p>
 *         Assigned to objects that provide a status, in our case, objects
 *         derived from
 *         {@link org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity}
 */
public interface Statusable {
    FormStatus getFormStatus();
}
