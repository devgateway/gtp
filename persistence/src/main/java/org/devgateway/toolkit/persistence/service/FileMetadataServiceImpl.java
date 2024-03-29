package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.repository.FileMetadataRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author idobre
 * @since 2019-03-04
 */
@Service
@Transactional(readOnly = true)
public class FileMetadataServiceImpl extends BaseJpaServiceImpl<FileMetadata> implements FileMetadataService {
    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Override
    public FileMetadata findByName(final String name) {
        return fileMetadataRepository.findByName(name);
    }

    @Override
    public FileMetadata findById(final long id) {
        return fileMetadataRepository.getOne(id);
    }

    @Override
    protected BaseJpaRepository<FileMetadata, Long> repository() {
        return fileMetadataRepository;
    }

    @Override
    public FileMetadata newInstance() {
        return null;
    }
}
