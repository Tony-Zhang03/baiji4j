package com.ctriposs.baiji.io;

import com.ctriposs.baiji.schema.Schema;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

public class JsonEncoder implements Encoder {

    private JsonGenerator out;

    public JsonEncoder(Schema sc, OutputStream out) throws IOException {
        this(sc, getJsonGenerator(out));
    }

    public JsonEncoder(Schema sc, JsonGenerator out) {
        this.out = out;
    }

    private static JsonGenerator getJsonGenerator(OutputStream out) throws IOException {
        return out == null ? null :
          new JsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
    }

    @Override
    public void writeNull() throws IOException {
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
    }

    @Override
    public void writeInt(int n) throws IOException {
    }

    @Override
    public void writeLong(long n) throws IOException {
    }

    @Override
    public void writeFloat(float f) throws IOException {
    }

    @Override
    public void writeDouble(double d) throws IOException {
    }

    @Override
    public void writeString(String str) throws IOException {
    }

    @Override
    public void writeBytes(byte[] bytes) throws IOException {
    }

    @Override
    public void writeEnum(int e) throws IOException {
    }

    @Override
    public void writeArrayStart() throws IOException {
    }

    @Override
    public void setItemCount(long itemCount) throws IOException {
    }

    @Override
    public void startItem() throws IOException {
    }

    @Override
    public void writeArrayEnd() throws IOException {
    }

    @Override
    public void writeMapStart() throws IOException {
    }

    @Override
    public void writeMapEnd() throws IOException {
    }

    @Override
    public void writeUnionIndex(int unionIndex) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }
}
