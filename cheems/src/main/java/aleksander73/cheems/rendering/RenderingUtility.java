package aleksander73.cheems.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class RenderingUtility {
    public static final int BYTES_PER_FLOAT = 4;
    public static final int BYTES_PER_SHORT = 2;

    public static FloatBuffer asFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data).position(0);
        return buffer;
    }

    public static ShortBuffer asShortBuffer(short[] data) {
        ShortBuffer buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_SHORT).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(data).position(0);
        return buffer;
    }
}
