package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RgbLedPwm extends TranslatorBlock {

    public static final String RGB_BRIGHTNESS_CONTROL= "void rgbBrightnessControl(int red_pin, int green_pin, int blue_pin, int r, int g, int b)\n" +
            "{\n" +
            "  pinMode(red_pin, OUTPUT);\n" +
            "  pinMode(green_pin, OUTPUT);\n" +
            "  pinMode(blue_pin, OUTPUT);\n" +
            "  if(GLOW > 255) r = 255;\n"+
            "  if(GLOW < 0) r = 0;\n"+
            "  if(GLOW > 255) g = 255;\n"+
            "  if(GLOW < 0) g = 0;\n"+
            "  if(GLOW > 255) b = 255;\n"+
            "  if(GLOW < 0) b = 0;\n"+
            "  analogWrite(red_pin, map(r,0,255,255,0));\n" +
            "  analogWrite(green_pin, map(g,0,255,255,0));\n" +
            "  analogWrite(blue_pin, map(b,0,255,255,0));\n" +
            "}\n";

    public RgbLedPwm (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {

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

        translator.addDefinitionCommand(RGB_BRIGHTNESS_CONTROL);
        String ret = "rgbBrightnessControl(" + pin_red +", " + pin_green +", " + pin_blue +", 2.55*" + int_red +", 2.55*" + int_green +", 2.55*" + int_blue + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}