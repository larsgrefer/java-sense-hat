package de.larsgrefer.sense_hat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by larsgrefer on 26.05.17.
 */
public class FrameBufferHelperTest {

    @Test
    public void testInt_white() {
        assertThat(FrameBufferHelper.toSenseHatColor(0xFF, 0xFF, 0xFF)).isEqualTo(0xFFFF);
    }

    @Test
    public void testDouble_white() {
        assertThat(FrameBufferHelper.toSenseHatColor(1d ,1d, 1d)).isEqualTo(0xFFFF);
    }

    @Test
    public void testString_white() {
        assertThat(FrameBufferHelper.toSenseHatColor("white")).isEqualTo(0xFFFF);
        assertThat(FrameBufferHelper.toSenseHatColor("FFF")).isEqualTo(0xFFFF);
        assertThat(FrameBufferHelper.toSenseHatColor("fff")).isEqualTo(0xFFFF);
        assertThat(FrameBufferHelper.toSenseHatColor("#FFF")).isEqualTo(0xFFFF);
        assertThat(FrameBufferHelper.toSenseHatColor("#FFFFFF")).isEqualTo(0xFFFF);
    }

    @Test
    public void testInt_black() {
        assertThat(FrameBufferHelper.toSenseHatColor(0x00, 0x00, 0x00)).isEqualTo(0x0000);
    }

    @Test
    public void testDouble_black() {
        assertThat(FrameBufferHelper.toSenseHatColor(0d ,0d, 0d)).isEqualTo(0x0000);
    }

    @Test
    public void testString_black() {
        assertThat(FrameBufferHelper.toSenseHatColor("black")).isEqualTo(0x0000);
        assertThat(FrameBufferHelper.toSenseHatColor("000")).isEqualTo(0x0000);
        assertThat(FrameBufferHelper.toSenseHatColor("#000")).isEqualTo(0x0000);
        assertThat(FrameBufferHelper.toSenseHatColor("000000")).isEqualTo(0x0000);
        assertThat(FrameBufferHelper.toSenseHatColor("#000000")).isEqualTo(0x0000);
    }

    @Test
    public void testInt_red() {
        assertThat(FrameBufferHelper.toSenseHatColor(0xFF, 0x00, 0x00)).isEqualTo(0xF800);
    }

    @Test
    public void testDouble_red() {
        assertThat(FrameBufferHelper.toSenseHatColor(1d ,0d, 0d)).isEqualTo(0xF800);
    }

    @Test
    public void testString_red() {
        assertThat(FrameBufferHelper.toSenseHatColor("red")).isEqualTo(0xF800);
        assertThat(FrameBufferHelper.toSenseHatColor("F00")).isEqualTo(0xF800);
        assertThat(FrameBufferHelper.toSenseHatColor("#F00")).isEqualTo(0xF800);
        assertThat(FrameBufferHelper.toSenseHatColor("FF0000")).isEqualTo(0xF800);
        assertThat(FrameBufferHelper.toSenseHatColor("#FF0000")).isEqualTo(0xF800);
    }

}