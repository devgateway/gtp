package org.devgateway.toolkit.persistence.util;

import java.io.Serializable;

@FunctionalInterface
public interface Function4<Param1Type, Param2Type, Param3Type, ReturnType> extends Serializable {
    ReturnType apply(Param1Type param1, Param2Type param2, Param3Type param3);
}
