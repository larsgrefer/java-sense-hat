package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.tester.Command;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author lgrefer
 */
@Parameters
public class SaveImageCommand implements Command {

    @Parameter(names = "--file", required = true)
    private File outFile;

    @Parameter(names = "--format")
    private String format;

    @Override
    public void run(SenseHat senseHat) throws IOException {

        if (format == null) {
            if (outFile.getName().endsWith(".png"))
                format = "png";
            if (outFile.getName().endsWith(".jpg"))
                format = "jpg";
            if (outFile.getName().endsWith(".gif"))
                format = "gif";
        }

        if (format != null) {
            ImageIO.write(senseHat.getImage(), format, outFile);
        } else {
            throw new IllegalArgumentException("Image format not given and could not be guessed");
        }
    }
}
