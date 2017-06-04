package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
@Getter
@Slf4j
public class TextDisplay {

    private final SenseHat senseHat;

    public void displayText(String text) throws IOException {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_USHORT_565_RGB);

        Graphics2D graphics = image.createGraphics();

        Font sansSerif = new Font("SansSerif", Font.PLAIN, 8);
        graphics.setFont(sansSerif);

        int i = graphics.getFontMetrics().stringWidth(text);

        for (int j = 0; j <= i; j++) {
            graphics.setColor(Color.BLACK);
            graphics.setPaint(Color.BLACK);
            graphics.fillRect(0, 0, 8, 8);

            graphics.setColor(Color.white);
            graphics.setPaint(Color.white);
            graphics.drawString(text, -j, 7);
            senseHat.setImage(image);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }

        graphics.dispose();



    }
}
