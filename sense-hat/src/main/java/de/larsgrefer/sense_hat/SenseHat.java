package de.larsgrefer.sense_hat;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static de.larsgrefer.sense_hat.FrameBufferHelper.toSenseHatColor;

@RequiredArgsConstructor
@ToString
public class SenseHat {

    public static final String SENSE_HAT_FB_NAME = "RPi-Sense FB";

    private final File frameBuffer;

    public SenseHat() throws IOException {
        frameBuffer = FrameBufferHelper.findDeviceFile()
                .orElseThrow(() -> new IllegalStateException("No Framebuffer found"));
    }

    public void fillColor(double red, double green, double blue) throws IOException {
        fillColorInternal(toSenseHatColor(red, green, blue));
    }

    public void fillColor(int red, int green, int blue) throws IOException {
        fillColorInternal(toSenseHatColor(red, green, blue));
    }

    public void fillColor(int color) throws IOException {
        fillColorInternal(toSenseHatColor(color));
    }

    public void fillColor(String color) throws IOException {
        fillColorInternal(toSenseHatColor(color));
    }

    private void fillColorInternal(int senseHatColor) throws IOException {
        try (BufferedSink buffer = Okio.buffer(Okio.sink(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                buffer.writeShortLe(senseHatColor);
            }
            buffer.flush();
        }
    }

    public void setPixel(int x, int y, int color) throws IOException {
        setPixelInternal(x, y, toSenseHatColor(color));
    }

    public void setPixel(int x, int y, String color) throws IOException {
        setPixelInternal(x, y, toSenseHatColor(color));
    }

    private void setPixelInternal(int x, int y, int i) throws IOException {
        if(x < 0 || x > 7) throw new IndexOutOfBoundsException("x");
        if(y < 0 || y > 7) throw new IndexOutOfBoundsException("y");

        int pixNum = 8*y + x;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(frameBuffer, "rw")) {

            randomAccessFile.seek(pixNum * 2);
            randomAccessFile.writeShort(i);
        }
    }
}
