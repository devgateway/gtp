package org.devgateway.toolkit.persistence.dao;

import org.hibernate.envers.Audited;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public abstract class AbstractStatusAuditableEntity extends AbstractAuditableEntity implements Statusable {
    @NotNull
    @Audited
    @Enumerated(EnumType.STRING)
    private FormStatus formStatus = FormStatus.NOT_STARTED;

    @Override
    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(final FormStatus formStatus) {
        this.formStatus = formStatus;
    }

    public boolean isPublished() {
        return formStatus != null && formStatus.isPublished();
    }
}
