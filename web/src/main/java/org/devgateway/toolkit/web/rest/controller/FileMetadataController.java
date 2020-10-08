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
package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.service.FileMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/files")
@CrossOrigin
public class FileMetadataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetadataController.class);

    @Autowired
    private FileMetadataService fileMetadataService;

    @CrossOrigin
    @ApiOperation(value = "Export a file with the provided {id}.")
    @RequestMapping(value = "/{id}", method = {POST, GET})
    public FileMetadata findFileMetadataById(@PathVariable final long id) {
        LOGGER.info("get file by id: " + id);
        return fileMetadataService.findById(id);
    }



    @ApiOperation(value = "Download a file with the provided {id}.")
    @RequestMapping(value = "/download/{id}", method = {POST, GET})
    public void download(final HttpServletResponse response, @PathVariable final long id) {
        try {
            FileMetadata fileMetadata = fileMetadataService.findById(id);

            response.setContentType("application/octet-stream");
            /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable
            by browser] right on browser while others(zip e.g) will be directly downloaded [may provide save as popup,
            based on your browser setting.]*/
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", fileMetadata.getName()));

            /* "Content-Disposition : attachment" will be directly download, may provide save as popup,
            based on your browser setting*/
            response.setContentLength(fileMetadata.getContent().getBytes().length);

            InputStream inputStream = new ByteArrayInputStream(fileMetadata.getContent().getBytes());
            //Copy bytes from source to destination(outputstream in this example), closes both streams.
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("Error: " + e);
            throw new BadRequestException("File not found");
        }
    }

    public static ResponseEntity<Resource> responseForFileMetadata(FileMetadata metadata, String ifNoneMatch) {
        String contentDispositionValue = ContentDisposition.builder("attachment")
                .filename(metadata.getName())
                .build()
                .toString();

        String etag = "\"" + metadata.getId().toString() + "\"";

        if (ifNoneMatch != null && (ifNoneMatch.equals("*") || ifNoneMatch.equals(etag))) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(etag)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .eTag(etag)
                    .cacheControl(CacheControl.noCache().mustRevalidate())
                    .contentType(MediaType.parseMediaType(metadata.getContentType()))
                    .contentLength(metadata.getSize())
                    .header("Content-Disposition", contentDispositionValue)
                    .body(new ByteArrayResource(metadata.getContent().getBytes()));
        }
    }
}
