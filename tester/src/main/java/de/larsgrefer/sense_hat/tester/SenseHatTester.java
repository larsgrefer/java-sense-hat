package de.larsgrefer.sense_hat.tester;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import de.larsgrefer.sense_hat.SenseHat;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars Grefer
 */
@Slf4j
@Getter
public class SenseHatTester {

    @Parameter(names = {"-h", "--help", "-?"}, help = true, description = "Show usage")
    private boolean printUsage = false;

    @Parameter(names = "--frame-buffer")
    private File frameBuffer;

    public static void main(String[] args) throws IOException, InterruptedException {
        SenseHatTester senseHatTester = new SenseHatTester();

        Map<String, Command> commands = new HashMap<>();

        commands.put("fill", new FillCommand());
        commands.put("set-pixel", new SetPixelCommand());
        commands.put("save-image", new SaveImageCommand());
        commands.put("load-image", new LoadImageCommand());
        commands.put("env", new EnvCommand());

        JCommander.Builder builder = JCommander.newBuilder()
                .addObject(senseHatTester);

        commands.forEach((name, cmd) -> builder.addCommand(name, cmd));

        JCommander jCommander = builder.build();
        jCommander.parse(args);

        if(senseHatTester.isPrintUsage()) {
            jCommander.usage();
            return;
        }

        String parsedCommand = jCommander.getParsedCommand();

        if (parsedCommand != null && commands.containsKey(parsedCommand)) {
            SenseHat senseHat;
            try {
                if(senseHatTester.getFrameBuffer() != null) {
                    senseHat = new SenseHat(senseHatTester.getFrameBuffer());
                } else {
                    senseHat = new SenseHat();
                }
            } catch (IllegalStateException e) {
                log.error(e.getLocalizedMessage(), e);
                return;
            }
            commands.get(parsedCommand).run(senseHat);
        } else {
            jCommander.usage();
        }
    }
}
