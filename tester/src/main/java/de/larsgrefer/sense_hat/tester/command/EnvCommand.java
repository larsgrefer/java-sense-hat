package de.larsgrefer.sense_hat.tester.command;

import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;

/**
 * Created by larsgrefer on 02.06.17.
 */
public class EnvCommand implements Command {
    @Override
    public void run(SenseHat senseHat) throws IOException {
        System.out.println("Pressure: " + senseHat.getPressure() + " mBar");
        System.out.println("Temp (Pressure): " +  senseHat.getTemperatureFromPressure() + " °C");
        System.out.println("Humidity: " +  senseHat.getHumidity() + " %");
        System.out.println("Temp (Humidity): " +  senseHat.getTemperatureFromHumidity() + " °C");
        System.out.println("Temp (combined): " +  senseHat.getTemperature() + " °C");
    }
}
