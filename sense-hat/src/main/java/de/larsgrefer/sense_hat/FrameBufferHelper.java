package de.larsgrefer.sense_hat;

import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Lars Grefer
 */
public class FrameBufferHelper {

    /**
     * Finds the correct frame buffer device for the sense HAT
     * and returns its /dev name.
     *
     * @return
     */
    public static Optional<File> findDeviceFile() throws IOException {

        File graphicsDir = new File("/sys/class/graphics");

        if(!graphicsDir.exists() || !graphicsDir.isDirectory()) {
            throw new RuntimeException(graphicsDir + "not found");
        }

        File[] frameBuffers = graphicsDir.listFiles(file -> file.isDirectory() && file.getName().startsWith("fb"));

        for (File possibleFrameBuffer : frameBuffers) {
            File nameFile = new File(possibleFrameBuffer, "name");

            if(nameFile.exists() && nameFile.isFile()) {
                String name = Okio.buffer(Okio.source(nameFile)).readUtf8();

                if(name.contains(SenseHat.SENSE_HAT_FB_NAME)) {
                    return Optional.of(new File("/dev", possibleFrameBuffer.getName()));
                }
            }
        }

        return Optional.empty();
    }

    public static int toSenseHatColor(int red, int green, int blue) {
        int r = (red >> 3) & 0x1F;
        int g = (green >> 2) & 0x3F;
        int b = (blue >> 3) & 0x1F;

        return  (r << 11) + (g << 5) + b;
    }

    public static int toSenseHatColor(double red, double green, double blue) {
        int r = (int) (red * 32);
        int g = (int) (green * 64);
        int b = (int) (blue * 32);

        return (r << 11) + (g << 5) + b;
    }

    public static int toSenseHatColor(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        return toSenseHatColor(red, green, blue);
    }
}
