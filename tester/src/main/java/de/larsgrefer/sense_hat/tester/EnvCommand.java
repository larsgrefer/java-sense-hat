package de.larsgrefer.sense_hat.tester;

import de.larsgrefer.sense_hat.SenseHat;

import java.io.IOException;

/**
 * Created by larsgrefer on 02.06.17.
 */
public class EnvCommand implements Command {
    @Override
    public void run(SenseHat senseHat) throws IOException {
        System.out.println("Pressure: " + senseHat.getPressure() + " hPa");
        System.out.println("Temp (Pressure): " +  senseHat.getTemperatureFromPressure() + " °C");
    }
}
