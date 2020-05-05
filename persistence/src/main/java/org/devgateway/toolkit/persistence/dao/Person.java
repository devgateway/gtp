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
package org.devgateway.toolkit.persistence.dao;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@Table(indexes = {@Index(columnList = "username")})
public class Person extends AbstractAuditableEntity implements UserDetails, Labelable {

    @ExcelExport
    private String username; // TODO remove username

    @ExcelExport
    private String firstName;

    @ExcelExport
    private String lastName;

    private String title;

    @ExcelExport
    private String email;

    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String plainPassword;

    @Transient
    @JsonIgnore
    private String plainPasswordCheck;

    private Boolean changePasswordNextSignIn;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Role> roles = new ArrayList<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    // flag if user/admin want to change password in profile account
    @Transient
    @JsonIgnore
    private boolean changeProfilePassword;

    private Boolean enabled = true;

    private String phone;

    private String recoveryToken;

    private ZonedDateTime recoveryTokenValidUntil;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(final String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPlainPasswordCheck() {
        return plainPasswordCheck;
    }

    public void setPlainPasswordCheck(final String plainPasswordCheck) {
        this.plainPasswordCheck = plainPasswordCheck;
    }

    public Boolean getChangePasswordNextSignIn() {
        return changePasswordNextSignIn;
    }

    public void setChangePasswordNextSignIn(final Boolean changePasswordNextSignIn) {
        this.changePasswordNextSignIn = changePasswordNextSignIn;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public void setAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean getChangeProfilePassword() {
        return changeProfilePassword;
    }

    public void setChangeProfilePassword(final boolean changeProfilePassword) {
        this.changeProfilePassword = changeProfilePassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    @NonNull
    public String toString() {
        if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
            return String.format("%s %s", firstName, lastName);
        }
        return String.format("%s %s", username, organization);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    public String getRecoveryToken() {
        return recoveryToken;
    }

    public void setRecoveryToken(String recoveryToken) {
        this.recoveryToken = recoveryToken;
    }

    public ZonedDateTime getRecoveryTokenValidUntil() {
        return recoveryTokenValidUntil;
    }

    public void setRecoveryTokenValidUntil(ZonedDateTime recoveryTokenValidUntil) {
        this.recoveryTokenValidUntil = recoveryTokenValidUntil;
    }

    @Override
    public void setLabel(String label) {
        firstName = label;
    }

    @Override
    public String getLabel() {
        return firstName;
    }

    @Override
    public String getLabel(String lang) {
        return firstName;
    }
}
