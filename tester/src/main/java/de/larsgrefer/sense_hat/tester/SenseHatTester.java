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

                long l = System.currentTimeMillis();

                while (true) {
                    long time = (System.currentTimeMillis() - l) % 5000;

                    int color = Color.HSBtoRGB((float) (time / 5000d), 1, 1);
                    senseHat.fillColor(color);
                    Thread.sleep(1);
                }
            }

            if(args.containsOption("set-pixel")) {
                int x = Integer.parseInt(args.getOptionValues("x").get(0));
                int y = Integer.parseInt(args.getOptionValues("y").get(0));

                String color = args.getOptionValues("color").get(0);

                senseHat.setPixel(x, y, color);
            }
        };
    }
}
