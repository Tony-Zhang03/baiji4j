package com.ctriposs.baiji;


import com.ctriposs.baiji.generic.DatumReader;
import com.ctriposs.baiji.generic.DatumWriter;
import com.ctriposs.baiji.io.JsonEncoder;
import com.ctriposs.baiji.specific.SpecificJsonWriter;
import com.ctriposs.baiji.specific.SpecificRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JsonSerializer implements Serializer {

    private static final ConcurrentMap<Class<?>, DatumReader> _readerCache =
            new ConcurrentHashMap<Class<?>, DatumReader>();

    private static final ConcurrentMap<Class<?>, DatumWriter> _writerCache =
            new ConcurrentHashMap<Class<?>, DatumWriter>();

    @Override
    public <T extends SpecificRecord> void serialize(T obj, OutputStream stream) throws IOException {
        DatumWriter<T> writer = getWriter(obj);
        writer.write(obj, new JsonEncoder(obj.getSchema(), stream));
    }

    @Override
    public <T extends SpecificRecord> T deserialize(Class<T> objClass, InputStream stream) throws IOException {
        return null;
    }

    private static <T extends SpecificRecord> DatumWriter<T> getWriter(T obj) {
        Class clazz = obj.getClass();
        DatumWriter<T> writer = _writerCache.get(clazz);
        if (writer == null) {
            writer = new SpecificJsonWriter<>(obj.getSchema());
            DatumWriter<T> existedWriter = _writerCache.putIfAbsent(clazz, writer);
            if (existedWriter != null) {
                writer = existedWriter;
            }
        }
        return writer;
    }
}
