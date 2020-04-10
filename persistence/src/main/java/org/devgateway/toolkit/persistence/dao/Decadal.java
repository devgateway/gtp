package org.devgateway.toolkit.persistence.dao;

/**
 * @author Nadejda Mandrescu
 */
public enum Decadal {
    FIRST(1),
    SECOND(2),
    THIRD(3);

    private final Integer value;

    private Decadal(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
