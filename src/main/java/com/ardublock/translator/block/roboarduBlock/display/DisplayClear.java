package com.ardublock.translator.block.roboarduBlock.display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DisplayClear extends TranslatorBlock {
    public DisplayClear (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String OLED_DEFINE="#define SCREEN_WIDTH 128 // OLED display width, in pixels\n" +
            "#define SCREEN_HEIGHT 64 // OLED display height, in pixels\n"+
            "#define OLED_RESET     4 // Reset pin # (or -1 if sharing Arduino reset pin)\n";

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String display="display";
        translator.addHeaderFile("Wire.h");
        translator.addHeaderFile("Adafruit_GFX.h");
        translator.addHeaderFile("Adafruit_SSD1306.h");
        translator.addDefinitionCommand("Adafruit_SSD1306 "+display+"(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);");
        translator.addSetupCommand();

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        AccMod=translatorBlock.toCode();



        return codePrefix + AccMod + codeSuffix;
    }

}
