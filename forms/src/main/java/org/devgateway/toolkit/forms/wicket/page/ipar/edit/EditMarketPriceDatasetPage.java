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
package org.devgateway.toolkit.forms.wicket.page.ipar.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditDatasePage;
import org.devgateway.toolkit.forms.wicket.page.ipar.lists.ListMarketPriceDatasetPage;
import org.devgateway.toolkit.persistence.dao.ipar.MarketDataset;
import org.devgateway.toolkit.persistence.dao.ipar.MarketPrice;
import org.devgateway.toolkit.persistence.service.ipar.DatasetService;
import org.devgateway.toolkit.persistence.service.ipar.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_EDITOR})
@MountPath("/editMarketPrice")
public class EditMarketPriceDatasetPage extends AbstractEditDatasePage<MarketDataset, MarketPrice> {

    private static final long serialVersionUID = -6069250112046118104L;
    private static final Logger logger = LoggerFactory.getLogger(EditMarketPriceDatasetPage.class);
    public static final String TEMPLATE_XLSX = "marketDataset-Template.xlsx";

    @SpringBean(name = "marketPriceImporter")
    private transient ImportService importService;

    @SpringBean(name = "marketPriceDatasetService")
    protected DatasetService service;

    public EditMarketPriceDatasetPage(final PageParameters parameters) {
        super(parameters, TEMPLATE_XLSX);
        this.jpaService = service;
        this.listPageClass = ListMarketPriceDatasetPage.class;
        this.importer = importService;
    }


}
