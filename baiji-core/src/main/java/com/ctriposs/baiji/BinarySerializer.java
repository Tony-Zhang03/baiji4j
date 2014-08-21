package com.ctriposs.baiji;

import com.ctriposs.baiji.generic.DatumReader;
import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.BinaryDecoder;
import com.ctriposs.baiji.io.BinaryEncoder;
import com.ctriposs.baiji.specific.SpecificDatumReader;
import com.ctriposs.baiji.specific.SpecificDatumWriter;
import com.ctriposs.baiji.specific.SpecificRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BinarySerializer implements Serializer {

    private static final ConcurrentMap<Class<?>, DatumReader> _readerCache =
            new ConcurrentHashMap<Class<?>, DatumReader>();

    private static final ConcurrentMap<Class<?>, DatumWriter> _writerCache =
            new ConcurrentHashMap<Class<?>, DatumWriter>();

    @Override
    public <T extends SpecificRecord> void serialize(T obj, OutputStream stream) throws IOException {
        DatumWriter<T> writer = getWriter(obj);
        writer.write(obj, new BinaryEncoder(stream));
    }

    @Override
    public <T extends SpecificRecord> T deserialize(Class<T> objClass, InputStream stream) throws IOException {
        DatumReader<T> reader = getReader(objClass);
        return reader.read(null, new BinaryDecoder(stream));
    }

    private static <T> DatumWriter<T> getWriter(T obj) {
        Class clazz = obj.getClass();
        DatumWriter<T> writer = _writerCache.get(clazz);
        if (writer == null) {
            writer = new SpecificDatumWriter<T>(((SpecificRecord) obj).getSchema());
            DatumWriter<T> existedWriter = _writerCache.putIfAbsent(clazz, writer);
            if (existedWriter != null) {
                writer = existedWriter;
            }
        }
        return writer;
    }

    private static <T> DatumReader<T> getReader(Class<T> clazz) {
        DatumReader<T> reader = _readerCache.get(clazz);
        if (reader == null) {
            SpecificRecord record;
            try {
                record = (SpecificRecord) clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            reader = new SpecificDatumReader<T>(record.getSchema());
            DatumReader<T> existedReader = _readerCache.putIfAbsent(clazz, reader);
            if (existedReader != null) {
                reader = existedReader;
            }
        }
        return reader;
    }
}
