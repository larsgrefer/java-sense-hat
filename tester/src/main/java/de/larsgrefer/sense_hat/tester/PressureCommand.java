package de.larsgrefer.sense_hat.tester;

import de.larsgrefer.sense_hat.SenseHat;

import java.io.IOException;

/**
 * Created by larsgrefer on 02.06.17.
 */
public class PressureCommand implements Command {
    @Override
    public void run(SenseHat senseHat) throws IOException {
        System.out.printf("Pressure: %shPa%n", senseHat.getPressure());
    }
}
