package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import de.larsgrefer.sense_hat.BinaryClock;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.SenseHatColor;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;

/**
 * Created by larsgrefer on 04.06.17.
 */
public class BinaryClockCommand implements Command {

    @Parameter(names = {"--color", "--foreground"})
    private String color = "0F0";

    @Parameter(names = {"--background"})
    private String background = "000";


    @Override
    public void run(SenseHat senseHat) throws IOException {
        BinaryClock binaryClock = new BinaryClock(senseHat);

        binaryClock.setForeground(SenseHatColor.fromString(color));
        binaryClock.setBackground(SenseHatColor.fromString(background));

        binaryClock.run();
    }
}
