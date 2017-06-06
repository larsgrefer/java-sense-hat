package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.tester.Command;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lgrefer
 */
@Parameters
public class LoadImageCommand implements Command {

    @Parameter(names = "--file", required = true)
    private File inFile;

    @Override
    public void run(SenseHat senseHat) throws IOException {

        BufferedImage image = ImageIO.read(inFile);
        senseHat.setImage(image);
    }
}
