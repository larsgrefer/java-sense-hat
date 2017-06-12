package de.larsgrefer.sense_hat.spring;

import de.larsgrefer.sense_hat.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SenseHatProperties.class)
public class SenseHatAutoConfiguration {

    @Autowired
    private SenseHatProperties senseHatProperties;

    @Bean
    public SenseHat senseHat() throws IOException {

        File frameBuffer = FrameBufferHelper.findDeviceFile().orElse(senseHatProperties.getFrameBuffer());

        if (frameBuffer == null) {
            log.debug("No FrameBuffer file found or configured. Using temp file.");
            frameBuffer = File.createTempFile("sense-hat", ".fb");
        }

        log.info("Using frame buffer: {}", frameBuffer);

        EnvironmentSensorAdapter sensorAdapter;

        switch (senseHatProperties.getSensorAdapterType()) {
            case PYTHON:
                sensorAdapter = new PythonSensorAdapter();
                break;
            case DUMMY:
                sensorAdapter = new DummySensorAdapter();
                break;
            default:
                throw new IllegalStateException("Unknown enum constant");
        }

        return new SenseHat(frameBuffer, sensorAdapter);
    }
}
