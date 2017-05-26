package de.larsgrefer.sense_hat.tester;

import de.larsgrefer.sense_hat.SenseHat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;

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
                String fill = args.getOptionValues("fill").get(0);

                senseHat.fillColor(fill);
            }

            if(args.containsOption("rainbow")) {
                for (int i = 0; i < 361; i = (i + 1) % 360) {
                    int color = Color.HSBtoRGB((float) (i / 360d), 1, 1);
                    senseHat.fillColor(color);
                }
            }
        };
    }
}
