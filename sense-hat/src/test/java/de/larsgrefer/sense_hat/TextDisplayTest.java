package de.larsgrefer.sense_hat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * Created by larsgrefer on 04.06.17.
 */
public class TextDisplayTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    SenseHat senseHat;

    @Before
    public void setUp() throws IOException {
        File fb = testFolder.newFile("fb");
        senseHat = new SenseHat(fb, new DummySensorAdapter());
    }

    @Test
    public void displayText() throws Exception {
        new TextDisplay(senseHat).displayText("Hallo Welt!");
    }

}