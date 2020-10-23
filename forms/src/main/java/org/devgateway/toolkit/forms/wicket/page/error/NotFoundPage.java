package org.devgateway.toolkit.forms.wicket.page.error;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@MountPath(value = "/error/not-found")
public class NotFoundPage extends ErrorPage {
    private static final long serialVersionUID = 2116205761426024000L;

    public NotFoundPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected AbstractLink getLink() {
        if (SecurityUtil.isAuthenticated()) {
            return super.getLink();
        }
        return new ExternalLink("link", "/");
    }
}
