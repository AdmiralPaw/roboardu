/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class touchSensor extends TranslatorBlock {
    public touchSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        translator.addDefinitionCommand("bool touchSensor(int Pin) {\n"
                + "  pinMode(Pin, INPUT);\n"
                + "  int touchCount[5];\n"
                + "  for (int i = 0; i < 5; i++) {\n"
                + "    touchCount[i] = digitalRead(Pin);\n"
                + "    delay(40);\n"
                + "  }\n"
                + "  if ((touchCount[0] && touchCount[1] && touchCount[2] && touchCount[3] && touchCount[4])) {\n"
                + "    return true;\n"
                + "  }\n"
                + "  return false;\n"
                + "}");



        return codePrefix + "touchSensor(" + pin + ")" + codeSuffix;
    }
}
