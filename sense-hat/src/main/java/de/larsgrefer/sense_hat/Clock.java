package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Created by larsgrefer on 06.06.17.
 */
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public abstract class Clock implements Runnable {

    private final SenseHat senseHat;

    private java.time.Clock clock = java.time.Clock.systemDefaultZone();

    private boolean stop = false;

    private Color background = Color.BLACK;

    @Override
    public void run() {
        BufferedImage bufferedImage = new BufferedImage(8,8,BufferedImage.TYPE_USHORT_565_RGB);

        while (!stop) {

            LocalTime now = LocalTime.now(clock);

            drawTime(bufferedImage, now);

            try {
                senseHat.fadeTo(bufferedImage, Duration.ofMillis(500));
            } catch (IOException e) {
                log.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }

    public void setBackground(SenseHatColor background) {
        this.background = background.toColor();
    }

    protected abstract void drawTime(BufferedImage bufferedImage, LocalTime now);
}
