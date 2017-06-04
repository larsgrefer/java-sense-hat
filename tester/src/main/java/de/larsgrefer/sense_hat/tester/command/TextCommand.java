package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.TextDisplay;
import de.larsgrefer.sense_hat.tester.Command;

import java.io.IOException;

@Parameters(commandDescription = "Show Text")
public class TextCommand implements Command {

    @Parameter
    private String text;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        new TextDisplay(senseHat).displayText(text);
    }
}
