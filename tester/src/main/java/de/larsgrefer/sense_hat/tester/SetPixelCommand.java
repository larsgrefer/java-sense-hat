package de.larsgrefer.sense_hat.tester;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.SenseHatColor;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Parameters(separators = "=", commandDescription = "Set one Pixel")
public class SetPixelCommand implements Command {

    @Parameter(names = "--x", required = true, validateValueWith = PixelIndexValidator.class)
    private int x;

    @Parameter(names = "--y", required = true, validateValueWith = PixelIndexValidator.class)
    private int y;

    @Parameter(names = "--color", required = true)
    private String color;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        senseHat.setPixel(x, y, SenseHatColor.fromString(color));
    }
}
