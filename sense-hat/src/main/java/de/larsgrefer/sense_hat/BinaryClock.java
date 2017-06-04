package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Created by larsgrefer on 04.06.17.
 */
@RequiredArgsConstructor
@Setter
@Getter
@Slf4j
public class BinaryClock implements Runnable {

    private final SenseHat senseHat;

    private Color background = Color.BLACK;
    private Color foreground = Color.GREEN;

    private Clock clock = Clock.systemDefaultZone();

    private boolean stop = false;

    @Override
    public void run() {
        BufferedImage bufferedImage = new BufferedImage(8,8,BufferedImage.TYPE_USHORT_565_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();

        while (!stop) {

            graphics.setBackground(background);
            graphics.setColor(foreground);

            graphics.clearRect(0,0,8,8);

            LocalTime now = LocalTime.now(clock);

            drawNumber(bufferedImage, 0, now.getHour()/10);
            drawNumber(bufferedImage, 1, now.getHour()%10);

            drawNumber(bufferedImage, 3, now.getMinute()/10);
            drawNumber(bufferedImage, 4, now.getMinute()%10);

            drawNumber(bufferedImage, 6, now.getSecond()/10);
            drawNumber(bufferedImage, 7, now.getSecond()%10);

            try {
                senseHat.fadeTo(bufferedImage, Duration.ofMillis(500));
            } catch (IOException e) {
                log.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
        }

        graphics.dispose();
    }

    private void drawNumber(BufferedImage image, int column, int number) {
        for(int row = 6, bit = 0; bit <= 3; row--, bit++) {
            if (((number >>> bit) & 1) == 1) {
                image.setRGB(column, row, foreground.getRGB());
            }
        }
    }

    public void setForeground(SenseHatColor foreground) {
        this.foreground = foreground.toColor();
    }

    public void setBackground(SenseHatColor background) {
        this.background = background.toColor();
    }
}
