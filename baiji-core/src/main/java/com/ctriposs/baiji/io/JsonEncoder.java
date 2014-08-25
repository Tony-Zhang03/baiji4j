package com.ctriposs.baiji.io;

import com.ctriposs.baiji.schema.Schema;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.io.IOException;
import java.io.OutputStream;

public class JsonEncoder implements Encoder {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private JsonGenerator out;

    public JsonEncoder(Schema sc, OutputStream out) throws IOException {
        this(sc, getJsonGenerator(out, false));
    }

    public JsonEncoder(Schema sc, OutputStream out, boolean pretty) throws IOException {
        this(sc, getJsonGenerator(out, pretty));
    }

    public JsonEncoder(Schema sc, JsonGenerator out) {
        this.out = out;
    }

    private static JsonGenerator getJsonGenerator(OutputStream out, boolean pretty) throws IOException {
        if (null == out)
            throw new NullPointerException("OutputStream can't be null");
        JsonGenerator g = new JsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
        if (pretty) {
            DefaultPrettyPrinter pp = new DefaultPrettyPrinter() {
                //@Override
                public void writeRootValueSeparator(JsonGenerator jg)
                        throws IOException
                {
                    jg.writeRaw(LINE_SEPARATOR);
                }
            };
            g.setPrettyPrinter(pp);
        } else {
            MinimalPrettyPrinter pp = new MinimalPrettyPrinter();
            pp.setRootValueSeparator(LINE_SEPARATOR);
            g.setPrettyPrinter(pp);
        }
        return g;
    }

    /**
     * Write the Start Object
     * @throws IOException
     */
    public void writeStartObject() throws IOException {
        out.writeStartObject();
    }

    /**
     * Write the End Object
     * @throws IOException
     */
    public void writeEndObject() throws IOException {
        out.writeEndObject();
    }

    @Override
    public void writeNull() throws IOException {
        out.writeNull();
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
        out.writeBoolean(b);
    }

    @Override
    public void writeInt(int n) throws IOException {
        out.writeNumber(n);
    }

    @Override
    public void writeLong(long n) throws IOException {
        out.writeNumber(n);
    }

    @Override
    public void writeFloat(float f) throws IOException {
        out.writeNumber(f);
    }

    @Override
    public void writeDouble(double d) throws IOException {
        out.writeNumber(d);
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
