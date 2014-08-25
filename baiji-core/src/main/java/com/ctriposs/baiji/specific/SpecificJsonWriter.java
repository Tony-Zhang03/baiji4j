package com.ctriposs.baiji.specific;

import com.ctriposs.baiji.exception.BaijiTypeException;
import com.ctriposs.baiji.generic.PreresolvingDatumWriter;
import com.ctriposs.baiji.io.Encoder;
import com.ctriposs.baiji.schema.EnumSchema;
import com.ctriposs.baiji.schema.RecordSchema;
import com.ctriposs.baiji.schema.Schema;

import java.io.IOException;
import java.util.List;

public class SpecificJsonWriter<T> extends PreresolvingDatumWriter<T> {


    public SpecificJsonWriter(Schema schema) {
        super(schema, new SpecificArrayAccess(), new DefaultMapAccess());
    }

    @Override
    protected void ensureRecordObject(RecordSchema recordSchema, Object value) {
    }

    @Override
    protected void writeRecordFields(Object record, RecordFieldWriter[] writers, Encoder encoder) throws IOException {
    }

    @Override
    protected void writeField(Object record, String fieldName, int fieldPos, ItemWriter writer, Encoder encoder) throws IOException {
    }

    @Override
    protected ItemWriter resolveEnum(EnumSchema es) {
        return null;
    }

    @Override
    protected boolean unionBranchMatches(Schema sc, Object obj) {
        return false;
    }

    private static class SpecificArrayAccess implements ArrayAccess {
        @Override
        public void ensureArrayObject(Object value) {
            if (!(value instanceof List)) {
                throw new BaijiTypeException("Array does not implement non-generic IList");
            }
        }

        @Override
        public long getArrayLength(Object value) {
            return ((List) value).size();
        }

        @Override
        public void writeArrayValues(Object array, ItemWriter valueWriter, Encoder encoder)
                throws IOException {
            List list = (List) array;
            for (int i = 0; i < list.size(); i++) {
                valueWriter.write(list.get(i), encoder);
            }
        }
    }
}
