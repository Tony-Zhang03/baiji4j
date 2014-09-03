package com.ctriposs.baiji.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BinaryCodecTestDouble extends BinaryCodecTestBase {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{new Object[]{0.0}, new Object[]{Float.MAX_VALUE},
                new Object[]{Double.MIN_NORMAL}, new Object[]{Double.MIN_VALUE}});
    }

    private final double _value;

    public BinaryCodecTestDouble(double value) {
        _value = value;
    }

    @Test
    public void testDouble() throws IOException {
        ItemDecoder decoder = new ItemDecoder() {
            @Override
            public Object decode(Decoder decoder) throws IOException {
                return decoder.readDouble();
            }
        };
        ItemEncoder encoder = new ItemEncoder() {
            @Override
            public void encode(Encoder encoder, Object obj) throws IOException {
                encoder.writeDouble((Double) obj);
            }
        };
        testRead(_value, decoder, encoder, 8);
    }
}
