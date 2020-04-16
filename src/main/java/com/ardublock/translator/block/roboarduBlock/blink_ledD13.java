/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class blink_ledD13 extends TranslatorBlock {

    /**
     *
     */
    public static final String BLINK_LED= "void LedBlink(int LED_PIN, int glow_time, int dark_time)\n" +
            "{\n" +
            "  pinMode(LED_PIN, OUTPUT);\n" +
            "  digitalWrite(LED_PIN, !HIGH);\n" +
            "  delay(glow_time);\n" +
            "  digitalWrite(LED_PIN, !LOW);\n" +
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
    public blink_ledD13(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String glow_time = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String dark_time = tb.toCode();

        translator.addDefinitionCommand(BLINK_LED);

        String ret ="LedBlink(" + "13" +", " + glow_time + ", " + dark_time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}
