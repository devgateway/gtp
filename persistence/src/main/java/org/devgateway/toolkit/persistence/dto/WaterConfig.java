package org.devgateway.toolkit.persistence.dto;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;

/**
 * @author Octavian Ciubotaru
 */
public class WaterConfig {

    private final List<PluviometricPost> posts;

    public WaterConfig(List<PluviometricPost> posts) {
        this.posts = posts;
    }

    public List<PluviometricPost> getPosts() {
        return posts;
    }
}
