//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.hollow.realth.api.util;

import java.nio.*;
import javax.imageio.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;

public class IconUtil
{
    public static final IconUtil INSTANCE;
    
    public ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(inputStream);
        final int[] nArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());
        final ByteBuffer byteBuffer = ByteBuffer.allocate(4 * nArray.length);
        Arrays.stream(nArray).map(n -> n << 8 | (n >> 24 & 0xFF)).forEach(byteBuffer::putInt);
        byteBuffer.flip();
        return byteBuffer;
    }
    
    static {
        INSTANCE = new IconUtil();
    }
}
