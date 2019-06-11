package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RgbLed extends TranslatorBlock {

    public static final String RGB_CONTROL= "void rgbControl(int red_pin, int green_pin, int blue_pin, bool r, bool g, bool b)\n" +
            "{\n" +
            "  pinMode(red_pin, OUTPUT);\n" +
            "  pinMode(green_pin, OUTPUT);\n" +
            "  pinMode(blue_pin, OUTPUT);\n" +
            "  digitalWrite(red_pin, !r);\n" +
            "  digitalWrite(green_pin, !g);\n" +
            "  digitalWrite(blue_pin, !b);\n" +
            "}\n";

    public RgbLed (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        String bool_red = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(4);
        String bool_green = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(5);
        String bool_blue = tb.toCode();

        translator.addDefinitionCommand(RGB_CONTROL);

        String ret ="rgbControl(" + pin_red +", " + pin_green +", " + pin_blue +", " + bool_red +", " + bool_green +", " + bool_blue + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}