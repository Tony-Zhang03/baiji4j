package com.ctriposs.baiji.specific;

import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.Encoder;
import com.ctriposs.baiji.schema.Schema;

import java.io.IOException;

public class SpecificJsonWriter implements DatumWriter {

    @Override
    public Schema getSchema() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void write(Object datum, Encoder out) throws IOException {
    }
}
