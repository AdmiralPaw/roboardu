package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class justLed extends TranslatorBlock {

    public static final String LED_GLOW= "void LedGlow(int LED_PIN, bool GLOW)\n" +
            "{\n" +
            "  pinMode(LED_PIN, OUTPUT);\n" +
            "  digitalWrite(LED_PIN, !GLOW);\n" +
            "}\n";

    public justLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pinNumber = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String glow_time = tb.toCode();

        translator.addDefinitionCommand(LED_GLOW);

        String ret ="LedGlow(" + pinNumber +", " + glow_time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}