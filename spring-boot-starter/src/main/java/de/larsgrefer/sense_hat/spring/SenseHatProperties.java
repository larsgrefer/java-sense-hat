package de.larsgrefer.sense_hat.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * @author Lars Grefer
 */
@Getter
@Setter
@ConfigurationProperties("sense-hat")
public class SenseHatProperties {

    private File frameBuffer;

    private SensorAdapterType sensorAdapterType = SensorAdapterType.PYTHON;

    public enum SensorAdapterType {
        PYTHON, DUMMY
    }
}
