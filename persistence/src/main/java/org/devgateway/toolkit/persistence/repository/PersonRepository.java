/*******************************************************************************
 * Copyright (c) 2015 Development Gateway, Inc and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 *
 * Contributors:
 * Development Gateway - initial API and implementation
 *******************************************************************************/
package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mpostelnicu
 */
@Transactional
public interface PersonRepository extends BaseJpaRepository<Person, Long> {
    Person findByUsername(String username);

    Person findByEmail(String email);

    Person findByRecoveryToken(String recoveryToken);

}
