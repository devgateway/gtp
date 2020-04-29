package org.devgateway.toolkit.persistence.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.devgateway.toolkit.persistence.dao.Decadal;

/**
 * @author Octavian Ciubotaru
 */
public class DecadalSerializer extends StdSerializer<Decadal> {

    public DecadalSerializer() {
        super(Decadal.class);
    }

    @Override
    public void serialize(Decadal decadal, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(decadal.getValue());
    }
}
