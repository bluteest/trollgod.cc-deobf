package me.hollow.realth.api.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.InputStream;

public class IconUtil {
    public static final IconUtil INSTANCE = new IconUtil();

    public ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int[] nArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * nArray.length);
        Arrays.stream(nArray).map(n -> n << 8 | n >> 24 & 0xFF).forEach(byteBuffer::putInt);
        byteBuffer.flip();
        return byteBuffer;
    }
}