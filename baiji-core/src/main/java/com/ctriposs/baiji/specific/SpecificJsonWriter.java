package com.ctriposs.baiji.specific;

import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.Encoder;
import com.ctriposs.baiji.io.JsonEncoder;
import com.ctriposs.baiji.schema.Field;
import com.ctriposs.baiji.schema.RecordSchema;
import com.ctriposs.baiji.schema.Schema;

import java.io.IOException;
import java.nio.ByteBuffer;

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
        writeRecord(root, datum, out);
    }

    /** Called to write data.*/
    protected void write(Schema schema, String fieldName, Object datum, JsonEncoder out) throws IOException {
        try {
            switch (schema.getType()) {
                case INT:
                    out.writeFieldName(fieldName);
                    out.writeInt(((Number)datum).intValue());
                    break;
                case LONG:
                    out.writeFieldName(fieldName);
                    out.writeLong((Long)datum);
                    break;
                case FLOAT:
                    out.writeFieldName(fieldName);
                    out.writeFloat((Float)datum);
                    break;
                case DOUBLE:
                    out.writeFieldName(fieldName);
                    out.writeDouble((Double)datum);
                    break;
                case BOOLEAN:
                    out.writeFieldName(fieldName);
                    out.writeBoolean((Boolean) datum);
                    break;
                case STRING:
                    out.writeFieldName(fieldName);
                    out.writeString(((CharSequence) datum).toString());
                    break;
                case BYTES:
                    out.writeFieldName(fieldName);
                    out.writeBytes((ByteBuffer) datum);
                    break;
                case NULL:
                    break;
                case ENUM:
                    break;
                case ARRAY:
                    break;
                case MAP:
                    break;
                case UNION:
                    break;
                default:
            }
        } catch (NullPointerException e) {

        }
    }

    /** Called to write a record.*/
    protected void writeRecord(Schema schema, Object datum, Encoder out) throws IOException {
        JsonEncoder jsonEncoder = (JsonEncoder) out;
        RecordSchema recordSchema = (RecordSchema) schema;
        jsonEncoder.writeStartObject();
        for (Field field : recordSchema.getFields()) {
            Object value = ((SpecificRecord) datum).get(field.getPos());
            writeField(field.getName(), value, field, jsonEncoder);
        }
        jsonEncoder.writeEndObject();
    }

    /** Called to write a single field of a record.*/
    protected void writeField(String fieldName, Object datum, Field f, JsonEncoder out) throws IOException {
        try {
            write(f.getSchema(), fieldName, datum, out);
        } catch (NullPointerException e) {

        }
    }
}
