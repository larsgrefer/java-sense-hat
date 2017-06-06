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

    public static final SenseHatColor BLACK = new SenseHatColor(0x0000);
    public static final SenseHatColor WHITE = new SenseHatColor(0xFFFF);
    public static final SenseHatColor GREY = BLACK.mix(WHITE);
    public static final SenseHatColor RED = new SenseHatColor(0b1111100000000000);
    public static final SenseHatColor GREEN = new SenseHatColor(0b0000011111100000);
    public static final SenseHatColor BLUE = new SenseHatColor(0b0000000000011111);
    public static final SenseHatColor CYAN = GREEN.plus(BLUE);
    public static final SenseHatColor MAGENTA = RED.plus(BLUE);
    public static final SenseHatColor YELLOW = RED.plus(GREEN);

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

    public float getRed() {
        return getRedRaw() / 31f;
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

    public SenseHatColor withRed(float red) {
        if (red < 0f || red > 1f) throw new IllegalArgumentException("red must be between 0.0 and 1.0");

        return withRedRaw((short) Math.round(red * 31f));
    }

    public short getGreenRaw() {
        return (short) ((senseHatColor >>> 5) & 0b111111);
    }

    public float getGreen() {
        return getGreenRaw() / 63f;
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

    public SenseHatColor withGreen(float green) {
        if (green < 0f || green > 1f) throw new IllegalArgumentException("green must be between 0.0 and 1.0");

        return withGreenRaw((short) Math.round(green * 63f));
    }

    public short getBlueRaw() {
        return (short) (senseHatColor & 0b11111);
    }

    public float getBlue() {
        return getBlueRaw() / 31f;
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

    public SenseHatColor withBlue(float blue) {
        if (blue < 0f || blue > 1f) throw new IllegalArgumentException("blue must be between 0.0 and 1.0");

        return withBlueRaw((short) Math.round(blue * 31f));
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

    public SenseHatColor multiply(float factor) {
        if (factor < 0f) {
            throw new IllegalArgumentException("The factor must not be negative");
        } else if (factor == 0f) {
            return BLACK;
        } else if (factor == 1f) {
            return this;
        } else if (Float.isInfinite(factor)) {
            return WHITE;
        }

        int newRed = (int) (getRedRaw() * factor + 0.5);
        int newGreen = (int) (getGreenRaw() * factor + 0.5);
        int newBlue = (int) (getBlueRaw() * factor + 0.5);

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

    public SenseHatColor divide(float divisor) {
        if (divisor < 0f) {
            throw new IllegalArgumentException("The divisor must not be negative");
        } else if (divisor == 0) {
            throw new IllegalArgumentException(new ArithmeticException());
        } else if (divisor == 1) {
            return this;
        }

        return multiply(1f / divisor);
    }

    public SenseHatColor mix(SenseHatColor other) {

        if (this.equals(other)) {
            return this;
        }

        float newRed = (getRed() + other.getRed()) / 2f;
        float newGreen = (getGreen() + other.getGreen()) / 2f;
        float newBlue = (getBlue() + other.getBlue()) / 2f;

        return fromRGB(newRed, newGreen, newBlue);
    }

    public SenseHatColor mix(SenseHatColor other, float factor) {
        if (factor < 0f || factor > 1f) throw new IllegalArgumentException("factor must be between 0.0 and 1.0");
        if (this.equals(other)) {
            return this;
        }

        if (factor == 0f) {
            return this;
        } else if (factor == 1f) {
            return other;
        }

        float thisFactor = 1f - factor;

        float thisRed = getRed() * thisFactor;
        float thisGreen = getGreen() * thisFactor;
        float thisBlue = getBlue() * thisFactor;

        float otherRed = other.getRed() * factor;
        float otherGreen = other.getGreen() * factor;
        float otherBlue = other.getBlue() * factor;

        float newRed = thisRed + otherRed;
        float newGreen = thisGreen + otherGreen;
        float newBlue = thisBlue + otherBlue;

        return fromRGB(newRed, newGreen, newBlue);
    }

    public static SenseHatColor fromRGB(int red, int green, int blue) {
        short r = (short) ((red >> 3) & 0b11111);
        short g = (short) ((green >> 2) & 0b111111);
        short b = (short) ((blue >> 3) & 0b11111);

        return new SenseHatColor(r, g, b);
    }

    public static SenseHatColor fromRGB(float red, float green, float blue) {
        if (red < 0f || red > 1f)
            throw new IllegalArgumentException("The red component must be between 0.0 and 1.0");
        if (green < 0f || green > 1f)
            throw new IllegalArgumentException("The green component must be between 0.0 and 1.0");
        if (blue < 0f || blue > 1f)
            throw new IllegalArgumentException("The blue component must be between 0.0 and 1.0");

        int r = (int) (red * 31 + 0.5);
        int g = (int) (green * 63 + 0.5);
        int b = (int) (blue * 31 + 0.5);

        return new SenseHatColor(r, g, b);
    }

    public static SenseHatColor fromColor(Color color) {
        return fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color toColor() {
        return new Color(getRed(), getGreen(), getBlue());
    }

    public static SenseHatColor fromRGB(int rgb) {
        return fromColor(new Color(rgb, false));
    }

    public static final Pattern SMALL_COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{1})([0-9A-F]{1})([0-9A-F]{1})");
    public static final Pattern COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})");

    public static SenseHatColor fromString(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return RED;
            case "green":
                return GREEN;
            case "blue":
                return BLUE;
            case "yellow":
                return YELLOW;
            case "pink":
            case "magenta":
                return MAGENTA;
            case "cyan":
                return CYAN;
            case "white":
                return WHITE;
            case "black":
                return BLACK;
            case "grey":
                return fromRGB(0.5f, 0.5f, 0.5f);
        }

        Matcher smallMatcher = SMALL_COLOR_HEX_PATTERN.matcher(color.toUpperCase());
        if (smallMatcher.matches()) {
            int red = Integer.parseInt(smallMatcher.group(1), 16);
            int green = Integer.parseInt(smallMatcher.group(2), 16);
            int blue = Integer.parseInt(smallMatcher.group(3), 16);

            return fromRGB(red / 15f, green / 15f, blue / 15f);
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
