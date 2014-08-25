package com.ctriposs.baiji.specific;

import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.Encoder;
import com.ctriposs.baiji.schema.Schema;

import java.io.IOException;

public class SpecificJsonWriter<T> implements DatumWriter<T> {

    private final Schema _schema;

    public SpecificJsonWriter(Schema schema) {
        _schema = schema;
    }

    @Override
    public Schema getSchema() {
        return _schema;
    }

    @Override
    public void write(T datum, Encoder out) throws IOException {
    }
}
