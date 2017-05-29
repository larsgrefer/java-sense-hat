package de.larsgrefer.sense_hat.tester;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

/**
 * @author Lars Grefer
 */
public class PixelIndexValidator implements IValueValidator<Integer> {
    @Override
    public void validate(String name, Integer value) throws ParameterException {
        if (value < 0 || value > 7) {
            throw new ParameterException("The pixel index must be between 0 and 7");
        }
    }
}
