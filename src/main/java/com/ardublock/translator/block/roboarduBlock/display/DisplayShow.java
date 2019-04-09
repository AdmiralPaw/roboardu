package com.ardublock.translator.block.roboarduBlock.display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DisplayShow extends TranslatorBlock {
    public DisplayShow (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String OLED_DEFINE="#define SCREEN_WIDTH 128 // OLED display width, in pixels\n" +
            "#define SCREEN_HEIGHT 64 // OLED display height, in pixels\n"+
            "#define OLED_RESET     4 // Reset pin # (or -1 if sharing Arduino reset pin)\n";

    @Override
    public String toCode()
    {
        String display="display";
        translator.addHeaderFile("Wire.h");
        translator.addHeaderFile("Adafruit_GFX.h");
        translator.addHeaderFile("Adafruit_SSD1306.h");
        translator.addDefinitionCommand("Adafruit_SSD1306 "+display+"(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);");
        translator.addSetupCommand("while(!" + display + ".begin(SSD1306_SWITCHCAPVCC, 0x3D) delay(1);\n" +
                display + ".display();\n" +
                "  delay(2000);\n" +
                "\n" +
                "  // Clear the buffer\n" +
                display + ".clearDisplay();\n" +
                display + ".display();\n");


        return codePrefix + display + ".display();\n" + codeSuffix;
    }

}
