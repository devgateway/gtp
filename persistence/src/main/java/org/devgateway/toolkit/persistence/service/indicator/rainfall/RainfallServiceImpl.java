package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.repository.indicator.RainfallRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class RainfallServiceImpl extends BaseJpaServiceImpl<Rainfall> implements RainfallService {
    @Autowired
    private RainfallRepository rainfallRepository;

    @Override
    protected BaseJpaRepository<Rainfall, Long> repository() {
        return rainfallRepository;
    }

    @Override
    public Rainfall newInstance() {
        return new Rainfall();
    }
}
