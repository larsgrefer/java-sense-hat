package de.larsgrefer.sense_hat;

/**
 * @author Lars Grefer
 */
public interface HumiditySensorAdapter {

    double getHumidity();
    double getTemperatureFromHumidity();
}
