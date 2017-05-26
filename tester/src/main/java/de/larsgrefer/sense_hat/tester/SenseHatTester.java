package de.larsgrefer.sense_hat.tester;

import de.larsgrefer.sense_hat.SenseHat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by larsgrefer on 26.05.17.
 */
@Slf4j
@SpringBootApplication
public class SenseHatTester {

    public static void main(String[] args) {
        SpringApplication.run(SenseHatTester.class, args);
    }

    @Autowired
    SenseHat senseHat;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if(args.containsOption("fill")) {
                String fill = args.getOptionValues("fill").get(0).toUpperCase();

                Matcher matcher = Pattern.compile("#([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})").matcher(fill);

                if(matcher.matches()) {
                    int red = Integer.parseInt(matcher.group(1), 16);
                    int green = Integer.parseInt(matcher.group(2), 16);
                    int blue = Integer.parseInt(matcher.group(3), 16);

                    log.info("filling with red={}, green0={}, blue={}", red, green, blue);

                    senseHat.fillColor(red/255d, green/255d, blue/255d);

                    log.info("SenseHat={}", senseHat);

                } else {
                    log.error("Failed to parse color {}", fill);
                }
            }
        };
    }
}
