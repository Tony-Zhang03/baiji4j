package com.ctriposs.baiji.specific;

import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.Encoder;
import com.ctriposs.baiji.schema.RecordSchema;
import com.ctriposs.baiji.schema.Schema;

import java.io.IOException;

public class SpecificJsonWriter<T> implements DatumWriter<T> {

    private Schema root;

    public SpecificJsonWriter(Schema root) {
        this.root = root;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public void write(T datum, Encoder out) throws IOException {
        write(root, datum, out);
    }

    /** Called to write record data.*/
    protected void write(Schema schema, Object datum, Encoder out) {
        if (schema instanceof RecordSchema) {

        }
    }
}
