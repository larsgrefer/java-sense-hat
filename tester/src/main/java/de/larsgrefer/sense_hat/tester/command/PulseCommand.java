package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.SenseHatColor;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Parameters(separators = "=")
public class PulseCommand implements Command {

    @Parameter(names = "--channels", variableArity = true)
    private String channels;

    @Parameter(names = "--duration")
    private int duration;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        long start = System.currentTimeMillis();

        boolean useRed = channels.contains("r");
        boolean useGreen = channels.contains("g");
        boolean useBlue = channels.contains("b");

        while (true) {
            long d = System.currentTimeMillis() - start;

            d = d % duration;

            float factor = d / (float)duration;

            senseHat.fill(SenseHatColor.fromRGB(
                    useRed ? factor : 0f,
                    useGreen ? factor : 0f,
                    useBlue ? factor : 0f
            ));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
