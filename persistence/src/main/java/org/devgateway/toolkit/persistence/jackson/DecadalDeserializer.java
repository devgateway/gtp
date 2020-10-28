package org.devgateway.toolkit.persistence.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.devgateway.toolkit.persistence.dao.Decadal;

import java.io.IOException;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalDeserializer extends StdDeserializer<Decadal> {
    private static final long serialVersionUID = -8058576464694148598L;

    public DecadalDeserializer() {
        super(Decadal.class);
    }

    @Override
    public Decadal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return Decadal.fromIndex(jsonParser.getIntValue());
    }
}
