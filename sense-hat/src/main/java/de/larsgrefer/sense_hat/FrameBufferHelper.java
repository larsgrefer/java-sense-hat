package de.larsgrefer.sense_hat;

import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Lars Grefer
 */
public class FrameBufferHelper {

    public static final String SENSE_HAT_FB_NAME = "RPi-Sense FB";

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

                if(name.contains(SENSE_HAT_FB_NAME)) {
                    return Optional.of(new File("/dev", possibleFrameBuffer.getName()));
                }
            }
        }

        return Optional.empty();
    }
}
