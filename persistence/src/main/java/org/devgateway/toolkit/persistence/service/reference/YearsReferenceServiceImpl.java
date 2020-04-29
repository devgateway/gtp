package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dao.reference.YearsReference;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public abstract class YearsReferenceServiceImpl<T extends YearsReference & Serializable>
        extends BaseJpaServiceImpl<T> implements YearsReferenceService<T> {

    @Autowired
    protected PluviometricPostService pluviometricPostService;

    @Override
    @Transactional(readOnly = false)
    public void initialize(T reference) {
        List<PluviometricPost> missing = new ArrayList<>();
        if (reference.getReferences().isEmpty()) {
            missing = pluviometricPostService.findAll();
        } else {
            List<Long> existing = reference.getReferences()
                    .stream()
                    .map(ref -> ref.getPluviometricPost().getId())
                    .collect(Collectors.toList());
            missing = pluviometricPostService.findAllByIdNotIn(existing);
        }
        if (!missing.isEmpty()) {
            List<? extends PluviometricPostHolder> postsRefStart = missing
                    .stream()
                    .map(pp -> addPluviometricPostReference(reference, pp))
                    .collect(Collectors.toList());
            repository().save(reference);
        }
    }

    protected abstract PluviometricPostHolder addPluviometricPostReference(T reference,
            PluviometricPost pluviometricPost);
}
