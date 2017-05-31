package de.larsgrefer.sense_hat;

import lombok.Value;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lars Grefer
 */
@Value
public class SenseHatColor {

    private int senseHatColor;

    SenseHatColor(int senseHatColor) {
        this.senseHatColor = senseHatColor;
    }

    SenseHatColor(int r, int g, int b) {
        senseHatColor = (r << 11) + (g << 5) + b;
    }

    public double getRed() {
        int red = (senseHatColor >>> 11) & 0x1F;

        return red / 31d;
    }

    public SenseHatColor withRed(short red) {
        if (red < 0 || red >= 32) throw new IllegalArgumentException("red must be between 0 and 31");

        int newColor = getSenseHatColor();

        newColor &= ~(0x1f << 11);
        if (red != 0) {
            newColor |= (red) << 11;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withRed(double red) {
        if (red < 0d || red > 1d) throw new IllegalArgumentException("red must be between 0.0 and 1.0");

        return withRed((short) (red * 31d));
    }

    public double getGreen() {
        int green = (senseHatColor >>> 5) & 0x3F;

        return green / 63d;
    }

    public SenseHatColor withGreen(short green) {
        if (green < 0 || green >= 64) throw new IllegalArgumentException("red must be between 0 and 63");

        int newColor = getSenseHatColor();

        newColor &= ~(0x3f << 5);
        if (green != 0) {
            newColor |= green << 5;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withGreen(double green) {
        if (green < 0d || green > 1d) throw new IllegalArgumentException("green must be between 0.0 and 1.0");

        return withGreen((short) (green * 63d));
    }

    public double getBlue() {
        int blue = senseHatColor & 0x1F;

        return blue / 31d;
    }

    public SenseHatColor withBlue(short blue) {
        if (blue < 0 || blue >= 32) throw new IllegalArgumentException("blue must be between 0 and 31");

        int newColor = getSenseHatColor();

        newColor &= ~0x1f;
        if (blue != 0) {
            newColor |= blue;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withBlue(double blue) {
        if (blue < 0d || blue > 1d) throw new IllegalArgumentException("blue must be between 0.0 and 1.0");

        return withBlue((short) (blue * 31d));
    }

    public static SenseHatColor fromRGB(int red, int green, int blue) {
        int r = (red >> 3) & 0x1F;
        int g = (green >> 2) & 0x3F;
        int b = (blue >> 3) & 0x1F;

        return new SenseHatColor(r, g, b);
    }

    public static SenseHatColor fromRGB(double red, double green, double blue) {
        if (red < 0d || red > 1d) throw new IllegalArgumentException("The red component must be between 0 and 1");
        if (green < 0d || green > 1d) throw new IllegalArgumentException("The green component must be between 0 and 1");
        if (blue < 0d || blue > 1d) throw new IllegalArgumentException("The blue component must be between 0 and 1");

        int r = (int) (red * 31);
        int g = (int) (green * 63);
        int b = (int) (blue * 31);

        return new SenseHatColor(r, g, b);
    }

    public static SenseHatColor fromColor(Color color) {
        return fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static SenseHatColor fromRGB(int rgb) {
        return fromColor(new Color(rgb, false));
    }

    public static final Pattern SMALL_COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{1})([0-9A-F]{1})([0-9A-F]{1})");
    public static final Pattern COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})");

    public static SenseHatColor fromString(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return new SenseHatColor(0xF800);
            case "green":
                return new SenseHatColor(0x7E0);
            case "blue":
                return new SenseHatColor(0x1F);
            case "white":
                return new SenseHatColor(0xFFFF);
            case "black":
                return new SenseHatColor(0x0000);
        }

        Matcher smallMatcher = SMALL_COLOR_HEX_PATTERN.matcher(color.toUpperCase());
        if (smallMatcher.matches()) {
            int red = Integer.parseInt(smallMatcher.group(1), 16);
            int green = Integer.parseInt(smallMatcher.group(2), 16);
            int blue = Integer.parseInt(smallMatcher.group(3), 16);

            return fromRGB(red / 15d, green / 15d, blue / 15d);
        }

        Matcher matcher = COLOR_HEX_PATTERN.matcher(color.toUpperCase());
        if (matcher.matches()) {
            int red = Integer.parseInt(matcher.group(1), 16);
            int green = Integer.parseInt(matcher.group(2), 16);
            int blue = Integer.parseInt(matcher.group(3), 16);

            return fromRGB(red, green, blue);
        }

        throw new IllegalArgumentException();
    }


}
