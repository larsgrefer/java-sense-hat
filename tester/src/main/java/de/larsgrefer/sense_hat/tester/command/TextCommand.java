package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.SenseHatColor;
import de.larsgrefer.sense_hat.TextDisplay;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Parameters(commandDescription = "Show Text", separators = "=")
public class TextCommand implements Command {

    @Parameter
    private List<String> text;

    @Parameter(names = {"--color", "--foreground"})
    private String color = "FFF";

    @Parameter(names = {"--background"})
    private String background = "000";

    @Parameter(names = {"--duration"})
    private int duration = 250;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        TextDisplay textDisplay = new TextDisplay(senseHat);


        textDisplay.setForeground(SenseHatColor.fromString(this.color));
        textDisplay.setBackground(SenseHatColor.fromString(this.background));
        textDisplay.setDuration(Duration.ofMillis(duration));

        for (String line : text) {
            textDisplay.displayText(line);
        }
    }
}
