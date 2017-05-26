package de.larsgrefer.sense_hat;

import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lars Grefer
 */
public class FrameBufferHelper {

    /**
     * Finds the correct frame buffer device for the sense HAT
     * and returns its /dev name.
     *
     * @return
     */
    public static Optional<File> findDeviceFile() throws IOException {

        File graphicsDir = new File("/sys/class/graphics");

        if(!graphicsDir.exists() || !graphicsDir.isDirectory()) {
            throw new RuntimeException(graphicsDir + "not found");
        }

        File[] frameBuffers = graphicsDir.listFiles(file -> file.isDirectory() && file.getName().startsWith("fb"));

        for (File possibleFrameBuffer : frameBuffers) {
            File nameFile = new File(possibleFrameBuffer, "name");

            if(nameFile.exists() && nameFile.isFile()) {
                String name = Okio.buffer(Okio.source(nameFile)).readUtf8();

                if(name.contains(SenseHat.SENSE_HAT_FB_NAME)) {
                    return Optional.of(new File("/dev", possibleFrameBuffer.getName()));
                }
            }
        }

        return Optional.empty();
    }

    public static int toSenseHatColor(int red, int green, int blue) {
        if (red < 0 || red > 255) throw new IllegalArgumentException("The red component must be between 0 and 255");
        if (green < 0 || green > 255) throw new IllegalArgumentException("The green component must be between 0 and 255");
        if (blue < 0 || blue > 255) throw new IllegalArgumentException("The blue component must be between 0 and 255");

        int r = (red >> 3) & 0x1F;
        int g = (green >> 2) & 0x3F;
        int b = (blue >> 3) & 0x1F;

        return (r << 11) + (g << 5) + b;
    }

    public static int toSenseHatColor(double red, double green, double blue) {
        if (red < 0d || red > 1d) throw new IllegalArgumentException("The red component must be between 0 and 1");
        if (green < 0d || green > 1d) throw new IllegalArgumentException("The green component must be between 0 and 1");
        if (blue < 0d || blue > 1d) throw new IllegalArgumentException("The blue component must be between 0 and 1");

        int r = (int) (red * 31);
        int g = (int) (green * 63);
        int b = (int) (blue * 31);

        return (r << 11) + (g << 5) + b;
    }

    public static int toSenseHatColor(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        return toSenseHatColor(red, green, blue);
    }

    public static final Pattern SMALL_COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{1})([0-9A-F]{1})([0-9A-F]{1})");
    public static final Pattern COLOR_HEX_PATTERN = Pattern.compile("#?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})");

    public static int toSenseHatColor(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return toSenseHatColor(255, 0, 0);
            case "green":
                return toSenseHatColor(0, 255, 0);
            case "blue":
                return toSenseHatColor(0, 0, 255);
            case "white":
                return toSenseHatColor(255, 255, 255);
            case "black":
                return toSenseHatColor(0, 0, 0);
        }

        Matcher smallMatcher = SMALL_COLOR_HEX_PATTERN.matcher(color.toUpperCase());
        if (smallMatcher.matches()) {
            int red = Integer.parseInt(smallMatcher.group(1), 16);
            int green = Integer.parseInt(smallMatcher.group(2), 16);
            int blue = Integer.parseInt(smallMatcher.group(3), 16);

            return toSenseHatColor(red / 15d, green / 15d, blue / 15d);
        }

        Matcher matcher = COLOR_HEX_PATTERN.matcher(color.toUpperCase());
        if (matcher.matches()) {
            int red = Integer.parseInt(matcher.group(1), 16);
            int green = Integer.parseInt(matcher.group(2), 16);
            int blue = Integer.parseInt(matcher.group(3), 16);

            return toSenseHatColor(red / 255d, green / 255d, blue / 255d);
        }

        throw new IllegalArgumentException();
    }
}
