package com.ardublock.translator.block.roboarduBlock.display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DisplayString extends TranslatorBlock {
    public DisplayString (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }


    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String display="display";
        translator.addHeaderFile("Wire.h");
        translator.addHeaderFile("Adafruit_GFX.h");
        translator.addHeaderFile("Adafruit_SSD1306.h");
        translator.addDefinitionCommand("Adafruit_SSD1306 "+display+"(128, 64, &Wire, -1);");
        translator.addSetupCommand("while(!" + display + ".begin(SSD1306_SWITCHCAPVCC, 0x3D) delay(1);\n" +
                display + ".display();\n" +
                "  delay(2000);\n" +
                "\n" +
                "  // Clear the buffer\n" +
                display + ".clearDisplay();" +
                display + ".display();\n");
        translator.addSetupCommand(display + ".setTextColor(WHITE);\n");


        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String DisplayString = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
        String StringSize = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
        String BeginX = translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
        String BeginY = translatorBlock.toCode();


        return codePrefix + display + ".setTextSize(" + StringSize + ");\n  " +
                display + ".setCursor(" + BeginX + ", " + BeginY + ");\n   " +
                display + ".println(F(" + DisplayString + "));\n" + codeSuffix;
    }

}