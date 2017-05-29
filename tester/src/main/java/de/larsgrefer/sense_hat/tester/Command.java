package de.larsgrefer.sense_hat.tester;

import de.larsgrefer.sense_hat.SenseHat;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
public interface Command {

    void run(SenseHat senseHat) throws IOException;
}
