package de.larsgrefer.sense_hat;

/**
 * @author Lars Grefer
 */
public interface PressureSensorAdapter {

    double getPressure();

    double getTemperatureFromPressure();
}
