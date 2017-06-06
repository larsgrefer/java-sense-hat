package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class TextDisplay {

    private final SenseHat senseHat;
    private Color background = Color.BLACK;
    private Color foreground = Color.WHITE;
    private Duration duration = Duration.ofMillis(250);

    private boolean big = false;

    public void displayText(String text) throws IOException {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_USHORT_565_RGB);

        Graphics2D graphics = image.createGraphics();

        if (big) {
            text = text.toUpperCase();
        }

        Font sansSerif = new Font("SansSerif", Font.PLAIN, big ? 10 : 8);
        graphics.setFont(sansSerif);
        int i = graphics.getFontMetrics().stringWidth(text) - 8;
        if (i <= 0) {
            i = 1;
        }

        long durationInMillis = duration.toMillis();

        for (int j = 0; j <= i; j++) {
            long start = System.currentTimeMillis();
            graphics.setColor(background);
            graphics.setPaint(background);
            graphics.fillRect(0, 0, 8, 8);

            graphics.setColor(foreground);
            graphics.setPaint(foreground);
            graphics.drawString(text, -j, big ? 8 : 7);
            senseHat.fadeTo(image, duration.dividedBy(2));

            long timeToSleep = durationInMillis - (System.currentTimeMillis() - start);
            if(timeToSleep > 0) {
                try {
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
            }
        }

        graphics.dispose();
    }

    public void setForeground(SenseHatColor foreground) {
        this.foreground = foreground.toColor();
    }

    public void setBackground(SenseHatColor background) {
        this.background = background.toColor();
    }
}
