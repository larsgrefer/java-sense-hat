package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import de.larsgrefer.sense_hat.*;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;

/**
 * Created by larsgrefer on 04.06.17.
 */
public class BinaryClockCommand implements Command {

    @Parameter(names = {"--background"})
    private String background = "000";

    @Parameter(names = "--big")
    boolean big;

    @Override
    public void run(SenseHat senseHat) throws IOException {

        Clock binaryClock;
        if(big) {
            binaryClock = new BigBinaryClock(senseHat);
        } else {
            binaryClock = new SmallBinaryClock(senseHat);
        }

        binaryClock.setBackground(SenseHatColor.fromString(background));

        binaryClock.run();
    }
}
