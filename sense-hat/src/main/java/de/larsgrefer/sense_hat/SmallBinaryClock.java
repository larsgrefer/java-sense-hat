package de.larsgrefer.sense_hat;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

/**
 * @author Lars Grefer
 */
@Setter
@Getter
@Slf4j
public class SmallBinaryClock extends Clock implements Runnable {

    private Color hours = Color.RED;
    private Color minutes = Color.GREEN;
    private Color seconds = Color.BLUE;

    public SmallBinaryClock(SenseHat senseHat) {
        super(senseHat);
    }

    protected void drawTime(BufferedImage bufferedImage, LocalTime now) {
        drawNumber(bufferedImage, 0, now.getHour()/10, hours);
        drawNumber(bufferedImage, 1, now.getHour()%10, hours);

        drawNumber(bufferedImage, 3, now.getMinute()/10, minutes);
        drawNumber(bufferedImage, 4, now.getMinute()%10, minutes);

        drawNumber(bufferedImage, 6, now.getSecond()/10, seconds);
        drawNumber(bufferedImage, 7, now.getSecond()%10, seconds);
    }

    private void drawNumber(BufferedImage image, int column, int number, Color color) {
        for (int row = 5, bit = 0; bit <= 3; row--, bit++) {
            if (((number >>> bit) & 1) == 1) {
                image.setRGB(column, row, color.getRGB());
            } else {
                image.setRGB(column, row, getBackground().getRGB());
            }
        }
    }

    public void setHours(SenseHatColor hours) {
        this.hours = hours.toColor();
    }

    public void setMinutes(SenseHatColor minutes) {
        this.minutes = minutes.toColor();
    }

    public void setSeconds(SenseHatColor seconds) {
        this.seconds = seconds.toColor();
    }
}
