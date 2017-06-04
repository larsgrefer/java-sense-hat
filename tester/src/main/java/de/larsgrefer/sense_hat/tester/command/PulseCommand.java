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

            double factor = d / (double)duration;

            senseHat.fill(SenseHatColor.fromRGB(
                    useRed ? factor : 0d,
                    useGreen ? factor : 0d,
                    useBlue ? factor : 0d
            ));

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
