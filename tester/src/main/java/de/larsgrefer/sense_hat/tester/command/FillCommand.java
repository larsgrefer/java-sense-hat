package de.larsgrefer.sense_hat.tester.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import de.larsgrefer.sense_hat.SenseHat;
import de.larsgrefer.sense_hat.SenseHatColor;
import de.larsgrefer.sense_hat.tester.Command;
import lombok.Getter;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Getter
@Parameters(
        separators = "=",
        commandDescription = "Fill the entire Matrix"
)
public class FillCommand implements Command {

    @Parameter(names = "--color")
    private String color;

    @Override
    public void run(SenseHat senseHat) throws IOException {
        if(color != null) {
            senseHat.fill(SenseHatColor.fromString(color));
            return;
        }
    }
}
