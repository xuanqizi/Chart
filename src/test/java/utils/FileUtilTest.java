package utils;

import org.junit.Test;

import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void readBufferAsShortInBigEndian() {
        short[] expected = new short[]{1, 30, 570, 2333, 16250, -16110};
        byte[] input = new byte[] {
                0x00, 0x01,
                0x00, 0x1e,
                0x02, 0x3a,
                0x09, 0x1d,
                0x3f, 0x7a,
                -0x3f, 0x12
        };
        short[] res = FileUtil.readBufferAsShort(input, 12, ByteOrder.BIG_ENDIAN);
        assertArrayEquals(expected, res);
    }

    @Test
    public void readBufferAsShortInLittleEndian() {
        byte[] input = new byte[] {
                0x00, 0x01,
                0x00, 0x1e,
                0x02, 0x3a,
                0x09, 0x1d,
        };
        short[] expected = new short[] {256, 7680, 14850, 7433};
        short[] res = FileUtil.readBufferAsShort(input, 8, ByteOrder.LITTLE_ENDIAN);
        assertArrayEquals(expected, res);
    }
}