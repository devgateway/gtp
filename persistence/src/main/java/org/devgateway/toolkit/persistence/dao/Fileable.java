package org.devgateway.toolkit.persistence.dao;

import java.util.Set;

/**
 * Created by Daniel Oliva
 */
public interface Fileable {

    Set<FileMetadata> getFiles();

    void setFiles(Set<FileMetadata> files);
}