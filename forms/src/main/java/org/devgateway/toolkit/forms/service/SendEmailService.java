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
package org.devgateway.toolkit.forms.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.devgateway.toolkit.forms.security.PasswordRecoveryProperties;
import org.devgateway.toolkit.persistence.dao.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * Service to send emails to users to validate email addresses or reset
 * passwords
 * 
 * @author mpostelnicu
 *
 */
@Component
public class SendEmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private PasswordRecoveryProperties recoveryProperties;

    /**
     * Send an email with a link that allows user to change the password.
     * 
     * @param person for whom we're resetting the pwd
     */
    public void sendEmailRecoveryEmail(final Person person) {
        String url;
        try {
            String token = person.getRecoveryToken();
            url = recoveryProperties.getBaseUrl() + "changeForgottenPassword/" + URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode recovery token.", e);
        }

        final SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(person.getEmail());
        msg.setFrom(recoveryProperties.getFrom());
        msg.setSubject("Recover your password");
        msg.setText("Dear " + person.getFirstName() + " " + person.getLastName() + ",\n\n"
                + "Please use this link to change your password for AD3 IPAR: " + url + "\n"
                + "This link will expire in an hour.\n"
                + "Your username is: " + person.getUsername() + ".\n\n"
                + "Thank you,\n"
                + "AD3 IPAR Team");

        try {
            javaMailSenderImpl.send(msg);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send password recovery email.", e);
        }
    }
}
