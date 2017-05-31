package de.larsgrefer.sense_hat;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiredArgsConstructor
@ToString
public class SenseHat {

    private final File frameBuffer;

    public SenseHat() throws IOException {
        frameBuffer = FrameBufferHelper.findDeviceFile()
                .orElseThrow(() -> new IllegalStateException("No Framebuffer found"));
    }

    public void fill(SenseHatColor senseHatColor) throws IOException {
        try (BufferedSink buffer = Okio.buffer(Okio.sink(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                buffer.writeShortLe(senseHatColor.getSenseHatColor());
            }
            buffer.flush();
        }
    }

    public void setPixel(int x, int y, SenseHatColor senseHatColor) throws IOException {
        if (x < 0 || x > 7) throw new IndexOutOfBoundsException("x");
        if (y < 0 || y > 7) throw new IndexOutOfBoundsException("y");

        int pixNum = 8 * y + x;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(frameBuffer, "rw")) {

            int color = senseHatColor.getSenseHatColor();

            randomAccessFile.seek(pixNum * 2);
            randomAccessFile.write(color & 0xFF);
            randomAccessFile.write((color >>> 8) & 0xFF);
        }
    }

    public BufferedImage getImage() throws IOException {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_USHORT_565_RGB);

        try (BufferedSource buffer = Okio.buffer(Okio.source(frameBuffer))) {
            short[] data = new short[64];

            for (int i = 0; i < 64; i++) {
                data[i] = buffer.readShortLe();
            }

            image.getRaster().setDataElements(0, 0, 8, 8, data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}