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
    private boolean antialiasing = true;

    public void displayText(String text) throws IOException {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_USHORT_565_RGB);

        Graphics2D graphics = image.createGraphics();

        Font sansSerif = new Font("SansSerif", Font.PLAIN, 8);
        graphics.setFont(sansSerif);
        if (antialiasing) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        }
        int i = graphics.getFontMetrics().stringWidth(text) - 8;

        for (int j = 0; j <= i; j++) {
            graphics.setColor(background);
            graphics.setPaint(background);
            graphics.fillRect(0, 0, 8, 8);

            graphics.setColor(foreground);
            graphics.setPaint(foreground);
            graphics.drawString(text, -j, 6);
            senseHat.fadeTo(image, duration.dividedBy(2));
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
