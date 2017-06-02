package de.larsgrefer.sense_hat;

import lombok.extern.slf4j.Slf4j;
import okio.Okio;

import java.io.IOException;

/**
 * @author Lars Grefer
 */
@Slf4j
public class PythonSensorAdapter implements PressureAdapter {
    @Override
    public double getPressure() {
        String rawPressure = execPythonCode("print sense.get_pressure()");

        return Double.parseDouble(rawPressure);
    }

    @Override
    public double getTemperatureFromPressure() {
        String rawTemp = execPythonCode("pring sense.get_temperature_from_pressure()");

        return Double.parseDouble(rawTemp);
    }

    private String execPythonCode(String line) {
        String code = "from sense_hat import SenseHat\n" +
                "sense = SenseHat()\n" +
                "\n" +
                line;

        try {
            Process process = Runtime.getRuntime().exec(new String[]{
                    "python", "-c", code
            });

            int exitCode = process.waitFor();

            if(exitCode == 0) {
                return Okio.buffer(Okio.source(process.getInputStream())).readUtf8();
            } else {
                log.error("Python exited with code {}", exitCode);
                String error = Okio.buffer(Okio.source(process.getErrorStream())).readUtf8();
                log.error(error);
                throw new RuntimeException(error);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
