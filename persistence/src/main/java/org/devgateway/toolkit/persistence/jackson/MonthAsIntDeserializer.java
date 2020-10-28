package org.devgateway.toolkit.persistence.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class MonthAsIntDeserializer extends StdDeserializer<Month> {
    private static final long serialVersionUID = -7188968765272609964L;

    public MonthAsIntDeserializer() {
        super(Month.class);
    }

    @Override
    public Month deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return Month.of(jsonParser.getIntValue());
    }
}
