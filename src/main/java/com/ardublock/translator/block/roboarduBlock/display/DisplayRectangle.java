package com.ardublock.translator.block.roboarduBlock.display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class DisplayRectangle extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public DisplayRectangle (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String OLED_DEFINE="#define SCREEN_WIDTH 128 // OLED display width, in pixels\n" +
            "#define SCREEN_HEIGHT 64 // OLED display height, in pixels\n"+
            "#define OLED_RESET     4 // Reset pin # (or -1 if sharing Arduino reset pin)\n";

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String display="display";
        translator.addHeaderFile("Wire.h");
        translator.addHeaderFile("Adafruit_GFX.h");
        translator.addHeaderFile("Adafruit_SSD1306.h");
        translator.addDefinitionCommand("Adafruit_SSD1306 "+display+"(128, 64, &Wire, -1);");
        translator.addSetupCommand("while(!" + display + ".begin(SSD1306_SWITCHCAPVCC, 0x3D)) delay(1);\n" +
                display + ".display();\n" +
                "  delay(2000);\n" +
                "\n" +
                "  // Clear the buffer\n" +
                display + ".clearDisplay();" +
                display + ".display();\n");


        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String BeginX = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
        String BeginY = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
        String SizeX = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
        String SizeY = translatorBlock.toCode();


        return codePrefix + display + ".drawRect("+ BeginX +", " + BeginY +", " + SizeX + ", " + SizeY + ", WHITE);\n" + codeSuffix;
    }

}
