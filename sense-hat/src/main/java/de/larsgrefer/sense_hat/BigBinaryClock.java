package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

/**
 * @author Lars Grefer
 */
@Getter
@Setter
public class BigBinaryClock extends Clock {

    private Color hours = Color.RED;
    private Color minutes = Color.BLUE;

    public BigBinaryClock(SenseHat senseHat) {
        super(senseHat);
    }

    protected void drawTime(BufferedImage bufferedImage, LocalTime now) {
        drawNumber(bufferedImage, 0, now.getHour() / 10, hours);
        drawNumber(bufferedImage, 1, now.getHour() % 10, hours);

        drawNumber(bufferedImage, 2, now.getMinute() / 10, minutes);
        drawNumber(bufferedImage, 3, now.getMinute() % 10, minutes);
    }

    private void drawNumber(BufferedImage image, int column, int number, Color color) {
        for (int row = 3, bit = 0; bit <= 3; row--, bit++) {
            if (((number >>> bit) & 1) == 1) {
                setBigPixel(image, column, row, color);
            } else {
                setBigPixel(image, column, row, getBackground());
            }
        }
    }

    private void setBigPixel(BufferedImage bufferedImage, int x, int y, Color color) {
        bufferedImage.setRGB(x * 2, y * 2, color.getRGB());
        bufferedImage.setRGB(x * 2, y * 2 + 1, color.getRGB());
        bufferedImage.setRGB(x * 2 + 1, y * 2, color.getRGB());
        bufferedImage.setRGB(x * 2 + 1, y * 2 + 1, color.getRGB());
    }


}
