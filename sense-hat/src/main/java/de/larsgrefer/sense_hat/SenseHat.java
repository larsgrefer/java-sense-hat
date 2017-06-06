package de.larsgrefer.sense_hat;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
@ToString
@Slf4j
public class SenseHat {

    private final File frameBuffer;
    private final EnvironmentSensorAdapter environmentSensorAdapter;

    public SenseHat() throws IOException {
        frameBuffer = FrameBufferHelper.findDeviceFile()
                .orElseThrow(() -> new IllegalStateException("No Framebuffer found"));
        environmentSensorAdapter = new PythonSensorAdapter();

    }

    public SenseHat(File frameBuffer) {
        this.frameBuffer = frameBuffer;
        environmentSensorAdapter = new PythonSensorAdapter();
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

        short[] data = getDisplayData();
        image.getRaster().setDataElements(0, 0, 8, 8, data);

        return image;
    }

    private short[] getDisplayData() throws IOException {
        short[] data = new short[64];
        try (BufferedSource buffer = Okio.buffer(Okio.source(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                data[i] = buffer.readShortLe();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private void setDisplayData(short[] displayData) throws IOException {
        try (BufferedSink buffer = Okio.buffer(Okio.sink(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                buffer.writeShortLe(displayData[i]);
            }
            buffer.flush();
        } catch (FileNotFoundException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void setImage(BufferedImage image) throws IOException {
        if (image.getType() == BufferedImage.TYPE_USHORT_565_RGB) {

            short[] data = (short[]) image.getRaster().getDataElements(0, 0, 8, 8, new short[64]);
            setDisplayData(data);

        } else {
            BufferedImage convertedImg = convertImage(image);

            setImage(convertedImg);
        }
    }

    private BufferedImage convertImage(BufferedImage image) {
        BufferedImage convertedImg = new BufferedImage(8, 8, BufferedImage.TYPE_USHORT_565_RGB);
        Graphics graphics = convertedImg.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        return convertedImg;
    }

    public void fadeTo(BufferedImage image, Duration duration) throws IOException {

        if(image.getType() == BufferedImage.TYPE_USHORT_565_RGB) {
            short[] oldData = getDisplayData();
            SenseHatColor[] oldColors = new SenseHatColor[64];

            short[] newData = (short[]) image.getRaster().getDataElements(0, 0, 8, 8, new short[64]);
            SenseHatColor[] newColors = new SenseHatColor[64];

            for (int i = 0; i < 64; i++) {
                oldColors[i] = new SenseHatColor(oldData[i]);
                newColors[i] = new SenseHatColor(newData[i]);
            }

            short[] data = new short[64];

            long sleepTime = Math.max(10, duration.toMillis() / 64);

            float durationInMillis = duration.toMillis();
            long start = System.currentTimeMillis();
            while (true) {
                long currentDuration = System.currentTimeMillis() - start;
                float factor = currentDuration / durationInMillis;

                if (factor < 1d) {
                    for (int i = 0; i < 64; i++) {
                        data[i] = (short) oldColors[i].mix(newColors[i], factor).getSenseHatColor();
                    }
                    setDisplayData(data);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                } else {
                    setDisplayData(newData);
                    break;
                }
            }

        } else {
            fadeTo(convertImage(image), duration);
        }
    }

    public SenseHatColor[][] getColors() throws IOException {
        SenseHatColor[][] colors = new SenseHatColor[8][8];

        try (BufferedSource buffer = Okio.buffer(Okio.source(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                colors[i / 8][i % 8] = new SenseHatColor(buffer.readShortLe());
            }

            return colors;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setColors(SenseHatColor[][] colors) throws IOException {
        if (colors.length != 8) throw new IllegalArgumentException(new IndexOutOfBoundsException());

        try (BufferedSink buffer = Okio.buffer(Okio.sink(frameBuffer))) {

            for (int i = 0; i < 64; i++) {
                SenseHatColor color = colors[i / 8][i % 8];
                buffer.writeShortLe(color == null ? 0 : color.getSenseHatColor());
            }
            buffer.flush();
        }
    }

    public double getPressure() {
        return environmentSensorAdapter.getPressure();
    }

    public double getTemperatureFromPressure() {
        return environmentSensorAdapter.getTemperatureFromPressure();
    }

    public double getHumidity() {
        return environmentSensorAdapter.getHumidity();
    }

    public double getTemperatureFromHumidity() {
        return environmentSensorAdapter.getTemperatureFromHumidity();
    }

    public double getTemperature() {
        return (getTemperatureFromHumidity() + getTemperatureFromHumidity()) / 2;
    }
}
