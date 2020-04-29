package org.devgateway.toolkit.persistence.jackson;

import java.io.IOException;
import java.time.Month;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Octavian Ciubotaru
 */
public class MonthAsIntSerializer extends StdSerializer<Month> {

    public MonthAsIntSerializer() {
        super(Month.class);
    }

    @Override
    public void serialize(Month month, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(month.getValue());
    }
}
