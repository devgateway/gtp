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
package org.devgateway.toolkit.forms.wicket.page;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.devgateway.toolkit.persistence.time.AD3Clock;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;

public class Footer extends Panel {

    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     *
     * @param markupId The components markup id.
     */
    public Footer(final String markupId) {
        super(markupId);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("version.properties");
        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("branch.properties");
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
            prop.load(inputStream2);
            inputStream.close();
            inputStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(new Label("toolkit-version", Model.of(prop.getProperty("toolkit.version"))));
        add(new Label("toolkit-branch", Model.of(prop.getProperty("toolkit.branch"))));
        add(new Label("toolkit-year", Calendar.getInstance().get(Calendar.YEAR)));
        if (AD3Clock.hasOffset()) {
            add(new Label("ad3clock",
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now(AD3Clock.systemDefaultZone()))));
        } else {
            add(new WebMarkupContainer("ad3clock").setVisibilityAllowed(false));
        }
    }
}
