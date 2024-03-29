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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author idobre
 * @since 11/13/14
 * <p>
 * Entity used to store the metadata of uploaded files
 */
@Entity
@Audited
@Table(indexes = {@Index(columnList = "name")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 50)
public class FileMetadata extends AbstractAuditableEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private FileContent content;

    private String name;

    private String contentType;

    private long size;

    public FileMetadata() {
    }

    public FileMetadata(Long id, String name, String contentType, FileContent content) {
        setId(id);
        this.name = name;
        this.contentType = contentType;
        this.content = content;
        this.size = content.getBytes().length;
    }

    private boolean isUserSupportDocument = false;

    @Override
    public String toString() {
        return name;
    }

    public FileContent getContent() {
        return content;
    }

    public void setContent(final FileContent content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public boolean isUserSupportDocument() {
        return isUserSupportDocument;
    }

    public void setUserSupportDocument(final boolean isUserSupportDocument) {
        this.isUserSupportDocument = isUserSupportDocument;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
