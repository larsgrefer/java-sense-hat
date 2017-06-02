package de.larsgrefer.sense_hat;

import java.util.Random;

/**
 * @author Lars Grefer
 */
public class DummySensorAdapter implements EnvironmentSensorAdapter {

    private Random random = new Random();

    @Override
    public double getHumidity() {
        return 50d + (random.nextGaussian() * 10);
    }

    @Override
    public double getTemperatureFromHumidity() {
        return 21d + random.nextGaussian();
    }

    @Override
    public double getPressure() {
        return 1023d + random.nextGaussian() * 20;
    }

    @Override
    public double getTemperatureFromPressure() {
        return 21d + random.nextGaussian();
    }
}
