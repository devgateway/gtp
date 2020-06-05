package org.devgateway.toolkit.web.rest.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

/**
 * @author Octavian Ciubotaru
 */
class ConstrainedFields {

    private final ConstraintDescriptions constraintDescriptions;

    ConstrainedFields(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
        return fieldWithPath(path).attributes(key("constraints")
                .value(String.join(". ", constraintDescriptions.descriptionsForProperty(path))));
    }
}
