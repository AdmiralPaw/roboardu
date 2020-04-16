package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class BlinkRgbLed extends TranslatorBlock {

    /**
     *
     */
    public static final String RGB_BLINK_CONTROL= "void rgbBlinkControl(int red_pin, int green_pin, int blue_pin, int r, int g, int b, int glow_time, int dark_time)\n" +
            "{\n" +
            "  pinMode(red_pin, OUTPUT);\n" +
            "  pinMode(green_pin, OUTPUT);\n" +
            "  pinMode(blue_pin, OUTPUT);\n" +
            "  if(r > 255) r = 255;\n"+
            "  if(r < 0) r = 0;\n"+
            "  if(g > 255) g = 255;\n"+
            "  if(g < 0) g = 0;\n"+
            "  if(b > 255) b = 255;\n"+
            "  if(b < 0) b = 0;\n"+
            "  analogWrite(red_pin, map(r,0,255,255,0));\n" +
            "  analogWrite(green_pin, map(g,0,255,255,0));\n" +
            "  analogWrite(blue_pin, map(b,0,255,255,0));\n" +
            "  delay(glow_time);\n" +
            "  analogWrite(red_pin, 255);\n" +
            "  analogWrite(green_pin, 255);\n" +
            "  analogWrite(blue_pin, 255);\n" +
            "  delay(dark_time);\n" +
            "}\n";

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public BlinkRgbLed (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {


        String servoSpecs = "";

        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pin_red = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String pin_green = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(2);
        String pin_blue = tb.toCode();

        tb = this.getRequiredTranslatorBlockAtSocket(3);
        String int_red = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(4);
        String int_green = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(5);
        String int_blue = tb.toCode();

        tb = this.getRequiredTranslatorBlockAtSocket(6);
        String glow_time = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(7);
        String dark_time = tb.toCode();

        translator.addDefinitionCommand(RGB_BLINK_CONTROL);
        String ret = "rgbBlinkControl(" + pin_red +", " + pin_green +", " + pin_blue +", 2.55*" + int_red +", 2.55*" + int_green +", 2.55*" + int_blue + ", "
                                    + glow_time + ", " + dark_time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}