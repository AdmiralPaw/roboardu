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


public class lightSensor extends TranslatorBlock
{

	public lightSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{   
            super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
        
        private static final String lightSensor = "int lightSensor(int Pin) {\n"
            + "  pinMode(Pin, INPUT);\n"
            + "  int arr[5];\n"
            + "  for (int i = 0; i < 5; i++) {\n"
            + "    arr[i] = analogRead(Pin);\n"
            + "    delay(10);\n"
            + "  }\n"
            + "  return map((arr[0] + arr[1] + arr[2] + arr[3] + arr[4]) / 5, 0, 1024, 100, 0);\n"
            + "}";

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
            translator.addDefinitionCommand(lightSensor);
            TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
            String ret = "lightSensor(" + translatorBlock.toCode() + ")";
            return codePrefix + ret + codeSuffix;
        }
}
