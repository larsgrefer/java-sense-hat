package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.TextDisplay;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;
import java.util.List;

@Parameters(commandDescription = "Show Text")
public class TextCommand implements Command {

    @Parameter
    private List<String> text;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        TextDisplay textDisplay = new TextDisplay(senseHat);
        for (String line : text) {
            textDisplay.displayText(line);
        }
    }
}
