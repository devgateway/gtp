package org.devgateway.toolkit.forms.wicket.validators;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.persistence.repository.ipar.PartnerRepository;

/**
 * @author Octavian Ciubotaru
 */
public class UniquePartnerValidator implements IValidator<String> {

    private PartnerRepository partnerRepository;

    private Long id;

    public UniquePartnerValidator(PartnerRepository partnerRepository, Long id) {
        this.partnerRepository = partnerRepository;
        this.id = id;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String partner = validatable.getValue();
        int count;
        if (id == null) {
            count = partnerRepository.countByName(partner);
        } else {
            count = partnerRepository.countByName(id, partner);
        }
        if (count > 0) {
            validatable.error(new ValidationError(this));
        }
    }
}
