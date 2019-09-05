package org.devgateway.toolkit.forms.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Octavian Ciubotaru
 */
@ConfigurationProperties(prefix = "ad3.password.recovery")
public class PasswordRecoveryProperties {

    /**
     * Base url that will be used to generate password recovery links. Must contain protocol and end in slash.
     * <p>Defaults to staging.</p>
     */
    private String baseUrl = "https://ad3.dgstg.org/";

    /**
     * Email of the sender.
     */
    private String from = "ad3@developmentgateway.org";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
