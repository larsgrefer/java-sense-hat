package de.larsgrefer.sense_hat;

/**
 * @author Lars Grefer
 */
public interface EnvironmentSensorAdapter {

    double getHumidity();

    double getTemperatureFromHumidity();

    double getPressure();

    double getTemperatureFromPressure();
}
