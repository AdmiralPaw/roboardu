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
    public static final String ARDUBLOCK_ANALOG_READ_DEFINE =
            /* If the pin was previously OUTPUT and is **quickly** analog read, the charge on the pin
             *  and the lack of "settling time" does affect the likely result of analogRead.
             * Always introducing a delay to settle the pin is not done here as it is overwhelmingly
             *   unnecessary, too slow to do every time and complicated to detect if it is actually needed.
             * If this needs to be done then implement https://github.com/arduino/Arduino/issues/4606
             *  and change to INPUT with a 10ms delay to settle only if the pinMode must be changed.
             *  Hide the ability to make Analog pins OUTPUT at the end of any pinLists!!!  */
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
        translator.addDefinitionCommand(ARDUBLOCK_ANALOG_READ_DEFINE);

        String ret = "!GetButton(12)";
        return codePrefix + ret + codeSuffix;
    }
}
