package de.larsgrefer.sense_hat.spring;

import de.larsgrefer.sense_hat.SenseHat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Configuration
public class SenseHatAutoConfiguration {

    @Bean
    public SenseHat senseHat() throws IOException {
        return new SenseHat();
    }
}
