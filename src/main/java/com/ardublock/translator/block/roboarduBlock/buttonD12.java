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

public class buttonD12 extends TranslatorBlock
{

    public static final String LIFT_FUNC_DEFINE = "void Lift_pin(int pinNumber, boolean status)\n"
            + "{\n"
            + "  pinMode(pinNumber, OUTPUT);\n"
            + "  digitalWrite(pinNumber, status);\n"
            + "}";
    public static final String BUTTON_FUNC_DEFINE =
            "boolean GetButton(int pin)\n" +
                    "{\n" +
                    "  boolean f = digitalRead(pin);\n" +
                    "  delay(10);\n" +
                    "  if (f == digitalRead(pin))\n" +
                    "  {\n" +
                    "    return f;\n" +
                    "  }\n" +
                    "  return 0;\n" +
                    "}";

    public buttonD12(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        translator.addDefinitionCommand(BUTTON_FUNC_DEFINE);
        translator.addDefinitionCommand(LIFT_FUNC_DEFINE);
        translator.addSetupCommand("Lift_pin(" + pin + ",true);");

        String ret = "!GetButton(" + pin + ")";
        return codePrefix + ret + codeSuffix;
    }
}
