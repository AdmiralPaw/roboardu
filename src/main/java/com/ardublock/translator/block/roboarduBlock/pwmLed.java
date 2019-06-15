package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class pwmLed extends TranslatorBlock {

    public static final String PWM_LED= "void LedPWM(int LED_PIN, int GLOW)\n" +
            "{\n" +
            "  if(GLOW > 100) GLOW = 100;\n"+
            "  if(GLOW < 0) GLOW = 0;\n"+
            "  pinMode(LED_PIN, OUTPUT);\n" +
            "  analogWrite(LED_PIN, map(GLOW,0,100,255,0));\n" +
            "}\n";

    public pwmLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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

        translator.addDefinitionCommand(PWM_LED);

        String ret ="LedPWM(" + pinNumber +", " + glow_time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}