package com.ctriposs.baiji.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An abstract {@link Encoder} for Baiji's binary encoding.
 * <p/>
 *
 * @see Encoder
 * @see Decoder
 */
public class BinaryEncoder implements Encoder {

    private final OutputStream _stream;

    public BinaryEncoder() {
        this(null);
    }

    public BinaryEncoder(OutputStream stream) {
        _stream = stream;
    }

    @Override
    public void writeNull() throws IOException {
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
        doWriteByte((byte) (b ? 1 : 0));
    }

    @Override
    public void writeInt(int value) throws IOException {
        writeLong(value);
    }

    public void writeLong(long value) throws IOException {
        long n = (value << 1) ^ (value >> 63);
        while ((n & ~0x7FL) != 0) {
            doWriteByte((byte) ((n | 0x80) & 0xFF));
            n >>>= 7;
        }
        doWriteByte((byte) n);
    }

    public void writeFloat(float value) throws IOException {
        int bits = Float.floatToRawIntBits(value);
        doWriteByte((byte) ((bits) & 0xFF));
        doWriteByte((byte) ((bits >> 8) & 0xFF));
        doWriteByte((byte) ((bits >> 16) & 0xFF));
        doWriteByte((byte) ((bits >> 24) & 0xFF));
    }

    @Override
    public void writeDouble(double value) throws IOException {
        long bits = Double.doubleToLongBits(value);
        doWriteByte((byte) ((bits) & 0xFF));
        doWriteByte((byte) ((bits >> 8) & 0xFF));
        doWriteByte((byte) ((bits >> 16) & 0xFF));
        doWriteByte((byte) ((bits >> 24) & 0xFF));
        doWriteByte((byte) ((bits >> 32) & 0xFF));
        doWriteByte((byte) ((bits >> 40) & 0xFF));
        doWriteByte((byte) ((bits >> 48) & 0xFF));
        doWriteByte((byte) ((bits >> 56) & 0xFF));
    }

    @Override
    public void writeBytes(byte[] value) throws IOException {
        writeLong(value.length);
        doWriteBytes(value);
    }

    @Override
    public void writeString(String value) throws IOException {
        writeBytes(value.getBytes("utf-8"));
    }

    @Override
    public void writeEnum(int value) throws IOException {
        writeLong(value);
    }

    @Override
    public void startItem() throws IOException {
    }

    @Override
    public void setItemCount(long value) throws IOException {
        if (value > 0) {
            writeLong(value);
        }
    }

    public void writeArrayStart() throws IOException {
    }

    public void writeArrayEnd() throws IOException {
        writeLong(0);
    }

    public void writeMapStart() throws IOException {
    }

    public void writeMapEnd() throws IOException {
        writeLong(0);
    }

    public void writeUnionIndex(int value) throws IOException {
        writeLong(value);
    }

    private void doWriteBytes(byte[] bytes) throws IOException {
        _stream.write(bytes, 0, bytes.length);
    }

    private void doWriteByte(byte b) throws IOException {
        _stream.write(b);
    }

    @Override
    public void flush() throws IOException {
        _stream.flush();
    }
}

