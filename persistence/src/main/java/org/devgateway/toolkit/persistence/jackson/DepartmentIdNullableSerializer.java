package org.devgateway.toolkit.persistence.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.devgateway.toolkit.persistence.dao.location.Department;

import java.io.IOException;

/**
 * @author Nadejda Mandrescu
 */
public class DepartmentIdNullableSerializer extends StdSerializer<Department> {
    private static final long serialVersionUID = 739335083011972140L;

    public DepartmentIdNullableSerializer() {
        super(Department.class);
    }

    @Override
    public void serialize(Department department, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (department.getId() == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeNumber(department.getId());
        }
    }
}
