package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import java.util.ResourceBundle;

public class BlinkLed extends TranslatorBlock {

    public static final String BLINK_LED= "void LedBlink(int LED_PIN, int glow_time, int dark_time)\n" +
            "{\n" +
            "  pinMode(LED_PIN, OUTPUT);\n" +
            "  digitalWrite(LED_PIN, HIGH);\n" +
            "  delay(glow_time);\n" +
            "  digitalWrite(LED_PIN, LOW);\n" +
            "  delay(dark_time);\n" +
            "}\n";

    public BlinkLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

        String servoSpecs = "";

        String pinNumber = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String glow_time = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(2);
        String dark_time = tb.toCode();

        translator.addDefinitionCommand(BLINK_LED);

        String ret ="LedBlink(" + pinNumber +", " + glow_time + ", " + dark_time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}

