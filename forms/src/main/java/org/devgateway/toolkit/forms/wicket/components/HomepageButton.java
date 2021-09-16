package org.devgateway.toolkit.forms.wicket.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import org.apache.wicket.Page;
import org.apache.wicket.model.ResourceModel;

/**
 * @author Octavian Ciubotaru
 */
public class HomepageButton<T> extends BootstrapBookmarkablePageLink<T> {

    public <P extends Page> HomepageButton(String id, Class<P> pageClass, IconType iconType) {
        super(id, pageClass, Buttons.Type.Default);

        setLabel(new ResourceModel(id + ".label"));
        setIconType(iconType);
        setSize(Buttons.Size.Large);
    }
}
