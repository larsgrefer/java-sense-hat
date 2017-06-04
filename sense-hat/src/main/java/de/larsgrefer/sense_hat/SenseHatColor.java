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
        if (r < 0 || r >= 32) throw new IllegalArgumentException("red must be between 0 and 31");
        if (g < 0 || g >= 64) throw new IllegalArgumentException("green must be between 0 and 63");
        if (b < 0 || b >= 32) throw new IllegalArgumentException("blue must be between 0 and 31");

        senseHatColor = (r << 11) + (g << 5) + b;
    }

    public short getRedRaw() {
        return (short) ((senseHatColor >>> 11) & 0b11111);
    }

    public double getRed() {
        return getRedRaw() / 31d;
    }

    public SenseHatColor withRedRaw(short red) {
        if (red < 0 || red >= 32) throw new IllegalArgumentException("red must be between 0 and 31");

        int newColor = getSenseHatColor();

        newColor &= 0b0000011111111111;
        if (red != 0) {
            newColor |= (red) << 11;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withRed(double red) {
        if (red < 0d || red > 1d) throw new IllegalArgumentException("red must be between 0.0 and 1.0");

        return withRedRaw((short) Math.round(red * 31d));
    }

    public short getGreenRaw() {
        return (short) ((senseHatColor >>> 5) & 0b111111);
    }

    public double getGreen() {
        return getGreenRaw() / 63d;
    }

    public SenseHatColor withGreenRaw(short green) {
        if (green < 0 || green >= 64) throw new IllegalArgumentException("green must be between 0 and 63");

        int newColor = getSenseHatColor();

        newColor &= 0b1111100000011111;
        if (green != 0) {
            newColor |= green << 5;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withGreen(double green) {
        if (green < 0d || green > 1d) throw new IllegalArgumentException("green must be between 0.0 and 1.0");

        return withGreenRaw((short) Math.round(green * 63d));
    }

    public short getBlueRaw() {
        return (short) (senseHatColor & 0b11111);
    }

    public double getBlue() {
        return getBlueRaw() / 31d;
    }

    public SenseHatColor withBlueRaw(short blue) {
        if (blue < 0 || blue >= 32) throw new IllegalArgumentException("blue must be between 0 and 31");

        int newColor = getSenseHatColor();

        newColor &= 0b1111111111100000;
        if (blue != 0) {
            newColor |= blue;
        }

        return new SenseHatColor(newColor);
    }

    public SenseHatColor withBlue(double blue) {
        if (blue < 0d || blue > 1d) throw new IllegalArgumentException("blue must be between 0.0 and 1.0");

        return withBlueRaw((short) Math.round(blue * 31d));
    }

    public SenseHatColor plus(SenseHatColor other) {
        int newRed = getRedRaw() + other.getRedRaw();
        int newGreen = getGreenRaw() + other.getGreenRaw();
        int newBlue = getBlueRaw() + other.getBlueRaw();

        if (newRed > 31) {
            newRed = 31;
        }
        if (newGreen > 63) {
            newGreen = 63;
        }
        if (newBlue > 31) {
            newBlue = 31;
        }

        return new SenseHatColor(newRed, newGreen, newBlue);
    }

    public SenseHatColor minus(SenseHatColor other) {
        int newRed = getRedRaw() - other.getRedRaw();
        int newGreen = getGreenRaw() - other.getGreenRaw();
        int newBlue = getBlueRaw() - other.getBlueRaw();

        if (newRed < 0) {
            newRed = 0;
        }
        if (newGreen < 0) {
            newGreen = 0;
        }
        if (newBlue < 0) {
            newBlue = 0;
        }

        return new SenseHatColor(newRed, newGreen, newBlue);
    }

    public SenseHatColor multiply(double factor) {
        int newRed = (int) Math.round(getRedRaw() * factor);
        int newGreen = (int) Math.round(getGreenRaw() * factor);
        int newBlue = (int) Math.round(getBlueRaw() * factor);

        if (newRed < 0) {
            newRed = 0;
        } else if (newRed > 31) {
            newRed = 31;
        }

        if (newGreen < 0) {
            newGreen = 0;
        } else if (newGreen > 63) {
            newGreen = 63;
        }

        if (newBlue < 0) {
            newBlue = 0;
        } else if (newBlue > 31) {
            newBlue = 31;
        }

        return new SenseHatColor(newRed, newGreen, newBlue);
    }

    public SenseHatColor divide(double divisor) {
        return multiply(1d / divisor);
    }

    public SenseHatColor mix(SenseHatColor other) {
        int newRed = getRedRaw() + other.getRedRaw();
        int newGreen = getGreenRaw() + other.getGreenRaw();
        int newBlue = getBlueRaw() + other.getBlueRaw();

        newRed = (int) Math.round(newRed / 2d);
        newGreen = (int) Math.round(newGreen / 2d);
        newBlue = (int) Math.round(newBlue / 2d);

        return new SenseHatColor(newRed, newGreen, newBlue);
    }

    public SenseHatColor mix(SenseHatColor other, double factor) {
        if (factor < 0d || factor > 1d) throw new IllegalArgumentException("factor must be between 0.0 and 1.0");

        if (factor == 0d) {
            return this;
        } else if (factor == 1d) {
            return other;
        }

        double thisFactor = 1d - factor;

        double thisRed = getRedRaw() * thisFactor;
        double thisGreen = getGreenRaw() * thisFactor;
        double thisBlue = getBlueRaw() * thisFactor;

        double otherRed = other.getRedRaw() * factor;
        double otherGreen = other.getGreenRaw() * factor;
        double otherBlue = other.getBlueRaw() * factor;

        int newRed = (int) Math.round(thisRed + otherRed);
        int newGreen = (int) Math.round(thisGreen + otherGreen);
        int newBlue = (int) Math.round(thisBlue + otherBlue);

        return new SenseHatColor(newRed, newGreen, newBlue);
    }

    public static SenseHatColor fromRGB(int red, int green, int blue) {
        int r = (red >> 3) & 0b11111;
        int g = (green >> 2) & 0b111111;
        int b = (blue >> 3) & 0b11111;

        return new SenseHatColor(r, g, b);
    }

    public static SenseHatColor fromRGB(double red, double green, double blue) {
        if (red < 0d || red > 1d) throw new IllegalArgumentException("The red component must be between 0.0 and 1.0");
        if (green < 0d || green > 1d) throw new IllegalArgumentException("The green component must be between 0.0 and 1.0");
        if (blue < 0d || blue > 1d) throw new IllegalArgumentException("The blue component must be between 0.0 and 1.0");

        int r = (int) Math.round(red * 31);
        int g = (int) Math.round(green * 63);
        int b = (int) Math.round(blue * 31);

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
                return new SenseHatColor(0b1111100000000000);
            case "green":
                return new SenseHatColor(0b0000011111100000);
            case "blue":
                return new SenseHatColor(0b0000000000011111);
            case "yellow":
                return new SenseHatColor(0b1111111111100000);
            case "pink":
            case "magenta":
                return new SenseHatColor(0b1111100000011111);
            case "cyan":
                return new SenseHatColor(0b0000011111111111);
            case "white":
                return new SenseHatColor(0b1111111111111111);
            case "black":
                return new SenseHatColor(0b0000000000000000);
            case "grey":
                return fromRGB(0.5, 0.5, 0.5);
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

    @Override
    public String toString() {
        return String.format("SenseHatColor(%04X)", senseHatColor);
    }
}
