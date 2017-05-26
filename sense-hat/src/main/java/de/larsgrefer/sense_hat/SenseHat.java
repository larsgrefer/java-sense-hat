package de.larsgrefer.sense_hat;

import lombok.RequiredArgsConstructor;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class SenseHat {

    public static final String SENSE_HAT_FB_NAME = "RPi-Sense FB";

    private final File frameBuffer;

    public SenseHat() throws IOException {
        frameBuffer = FrameBufferHelper.findDeviceFile()
                .orElseThrow(() -> new IllegalStateException("No Framebuffer found"));
    }

    public void fillColor(double red, double green, double blue) throws IOException {
        int color = FrameBufferHelper.toSenseHatColor(red, green, blue);

        try (BufferedSink buffer = Okio.buffer(Okio.sink(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                buffer.writeShort(color);
            }
        }
    }


}
