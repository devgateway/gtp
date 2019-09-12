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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mpostelnicu
 */
@Transactional
public interface PersonRepository extends BaseJpaRepository<Person, Long> {
    Person findByUsername(String username);

    Person findByEmail(String email);

    Person findByRecoveryToken(String recoveryToken);

    @Query("select distinct p "
            + "from Person p "
            + "join p.roles as r "
            + "where (lower(p.firstName) like %:term% "
            + "or lower(p.lastName) like %:term%) "
            + "and r.authority='ROLE_FOCAL_POINT'")
    Page<Person> findFocalPoints(@Param("term") String term, Pageable page);

    @Query("select distinct p "
            + "from Person p "
            + "join p.roles as r "
            + "where r.authority='ROLE_FOCAL_POINT'")
    Page<Person> findFocalPoints(Pageable page);
}
