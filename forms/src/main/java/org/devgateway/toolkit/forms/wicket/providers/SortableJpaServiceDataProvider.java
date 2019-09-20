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
package org.devgateway.toolkit.forms.wicket.providers;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.models.PersistableJpaRepositoryModel;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @author mpostelnicu
 * <p>
 * Smart generic {@link SortableDataProvider} that binds to {@link BaseJpaService}
 */
public class SortableJpaServiceDataProvider<T extends GenericPersistable & Serializable>
        extends SortableDataProvider<T, String> implements IFilterStateLocator<JpaFilterState<T>> {

    private final BaseJpaService<T> jpaService;

    private JpaFilterState<T> filterState;

    /**
     * Always provide a proxy jpaService here! For example one coming from a {@link SpringBean}
     *
     * @param jpaService
     */
    public SortableJpaServiceDataProvider(final BaseJpaService<T> jpaService) {
        this.jpaService = jpaService;
    }

    /**
     * Translates from a {@link SortParam} to a Spring {@link Sort}
     */
    private Sort buildSort() {
        if (getSort() == null) {
            return Sort.unsorted();
        }
        List<String> properties = ImmutableList.copyOf(StringUtils.split(getSort().getProperty(), ';'));
        return new Sort(getSort().isAscending() ? Direction.ASC : Direction.DESC, properties);
    }

    /**
     * @see SortableDataProvider#iterator(long, long)
     */
    @Override
    public Iterator<? extends T> iterator(final long first, final long count) {
        int page = (int) ((double) first / WebConstants.PAGE_SIZE);
        PageRequest pageRequest = PageRequest.of(page, WebConstants.PAGE_SIZE, buildSort());
        final Page<T> findAll = jpaService.findAll(filterState.getSpecification(), pageRequest);
        return findAll.iterator();
    }

    @Override
    public long size() {
        return jpaService.count(filterState.getSpecification());
    }

    /**
     * This ensures that the object is detached and reloaded after
     * deserialization of the page, since the
     * {@link PersistableJpaRepositoryModel} is also loadabledetachable
     */
    @Override
    public IModel<T> model(final T object) {
        return new PersistableJpaRepositoryModel<T>(object, jpaService);
    }

    @Override
    public JpaFilterState<T> getFilterState() {
        return filterState;
    }

    @Override
    public void setFilterState(final JpaFilterState<T> filterState) {
        this.filterState = filterState;
    }
}
