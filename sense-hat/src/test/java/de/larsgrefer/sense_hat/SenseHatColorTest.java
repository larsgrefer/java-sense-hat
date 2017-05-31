package de.larsgrefer.sense_hat;

import org.assertj.core.data.Offset;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by larsgrefer on 29.05.17.
 */
public class SenseHatColorTest {
    @Test
    public void testGetRed() throws Exception {
        assertThat(new SenseHatColor(0xFFFF).getRed()).isEqualTo(1);
        assertThat(new SenseHatColor(0xF800).getRed()).isEqualTo(1);
        assertThat(new SenseHatColor(0x0000).getRed()).isEqualTo(0);
    }

    @Test
    public void testSetRed() {
        SenseHatColor color = new SenseHatColor(0);

        color = color.withRed(1d);
        assertThat(color.getRed()).isEqualTo(1);

        color = color.withRed(0.5);
        assertThat(color.getRed()).isEqualTo(0.5, Offset.offset(1/31d));

        color = color.withRed(0);
        assertThat(color.getRed()).isEqualTo(0, Offset.offset(1/31d));
    }

    @Test
    public void getGreen() throws Exception {
        assertThat(new SenseHatColor(0xFFFF).getGreen()).isEqualTo(1);
        assertThat(new SenseHatColor(0x07E0).getGreen()).isEqualTo(1);
        assertThat(new SenseHatColor(0x0000).getGreen()).isEqualTo(0);
    }

    @Test
    public void getBlue() throws Exception {
        assertThat(new SenseHatColor(0xFFFF).getBlue()).isEqualTo(1);
        assertThat(new SenseHatColor(0x003F).getBlue()).isEqualTo(1);
        assertThat(new SenseHatColor(0x0000).getBlue()).isEqualTo(0);
    }

    @Test
    public void testInt_white() {
        assertThat(SenseHatColor.fromRGB(0xFF, 0xFF, 0xFF).getSenseHatColor()).isEqualTo(0xFFFF);
    }

    @Test
    public void testDouble_white() {
        assertThat(SenseHatColor.fromRGB(1d ,1d, 1d).getSenseHatColor()).isEqualTo(0xFFFF);
    }

    @Test
    public void testString_white() {
        assertThat(SenseHatColor.fromString("white").getSenseHatColor()).isEqualTo(0xFFFF);
        assertThat(SenseHatColor.fromString("FFF").getSenseHatColor()).isEqualTo(0xFFFF);
        assertThat(SenseHatColor.fromString("fff").getSenseHatColor()).isEqualTo(0xFFFF);
        assertThat(SenseHatColor.fromString("#FFF").getSenseHatColor()).isEqualTo(0xFFFF);
        assertThat(SenseHatColor.fromString("#FFFFFF").getSenseHatColor()).isEqualTo(0xFFFF);
    }

    @Test
    public void testInt_black() {
        assertThat(SenseHatColor.fromRGB(0x00, 0x00, 0x00).getSenseHatColor()).isEqualTo(0x0000);
    }

    @Test
    public void testDouble_black() {
        assertThat(SenseHatColor.fromRGB(0d ,0d, 0d).getSenseHatColor()).isEqualTo(0x0000);
    }

    @Test
    public void testString_black() {
        assertThat(SenseHatColor.fromString("black").getSenseHatColor()).isEqualTo(0x0000);
        assertThat(SenseHatColor.fromString("000").getSenseHatColor()).isEqualTo(0x0000);
        assertThat(SenseHatColor.fromString("#000").getSenseHatColor()).isEqualTo(0x0000);
        assertThat(SenseHatColor.fromString("000000").getSenseHatColor()).isEqualTo(0x0000);
        assertThat(SenseHatColor.fromString("#000000").getSenseHatColor()).isEqualTo(0x0000);
    }

    @Test
    public void testInt_red() {
        assertThat(SenseHatColor.fromRGB(0xFF, 0x00, 0x00).getSenseHatColor()).isEqualTo(0xF800);
    }

    @Test
    public void testDouble_red() {
        assertThat(SenseHatColor.fromRGB(1d ,0d, 0d).getSenseHatColor()).isEqualTo(0xF800);
    }

    @Test
    public void testString_red() {
        assertThat(SenseHatColor.fromString("red").getSenseHatColor()).isEqualTo(0xF800);
        assertThat(SenseHatColor.fromString("F00").getSenseHatColor()).isEqualTo(0xF800);
        assertThat(SenseHatColor.fromString("#F00").getSenseHatColor()).isEqualTo(0xF800);
        assertThat(SenseHatColor.fromString("FF0000").getSenseHatColor()).isEqualTo(0xF800);
        assertThat(SenseHatColor.fromString("#FF0000").getSenseHatColor()).isEqualTo(0xF800);
    }

    @Test
    public void testInt_blue() {
        assertThat(SenseHatColor.fromRGB(0x00, 0x00, 0xFF).getSenseHatColor()).isEqualTo(0x001F);
    }

    @Test
    public void testDouble_blue() {
        assertThat(SenseHatColor.fromRGB(0d ,0d, 1d).getSenseHatColor()).isEqualTo(0x001F);
    }

    @Test
    public void testString_blue() {
        assertThat(SenseHatColor.fromString("blue").getSenseHatColor()).isEqualTo(0x001F);
        assertThat(SenseHatColor.fromString("00F").getSenseHatColor()).isEqualTo(0x001F);
        assertThat(SenseHatColor.fromString("#00F").getSenseHatColor()).isEqualTo(0x001F);
        assertThat(SenseHatColor.fromString("0000FF").getSenseHatColor()).isEqualTo(0x001F);
        assertThat(SenseHatColor.fromString("#0000FF").getSenseHatColor()).isEqualTo(0x001F);
    }

}