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
	
        
        private static final String lightSensor = "" +
                "void MotorsRightDegrees(int Speed, int Deegree)\n" +
                "{\n" +
                "  unsigned long long nEncoder1Start = nEncoder1;\n" +
                "  unsigned long long nEncoder2Start = nEncoder2;\n" +
                "\n" +
                "  unsigned long long TimeStart = millis();\n" +
                "\n" +
                "  Motors(Speed,-Speed);\n" +
                "\n" +
                "  while(nEncoder1 - nEncoder1Start < Deegree*0.3 || nEncoder2 - nEncoder2Start < Deegree*0.3)\n" +
                "  {\n" +
                "    delay(1);\n" +
                "  }\n" +
                "\n" +
                "  MotorsStop();\n" +
                "}\n";

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
            translator.addDefinitionCommand(lightSensor);
            translator.addSetupCommand("InitMotors();");
            translator.addSetupCommand("InitEnc(ON);");
            TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
            String ret = "MotorsRightDegrees(" + translatorBlock.toCode() + ", ";
            translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
            ret = ret + translatorBlock.toCode() + ");";
            return codePrefix + ret + codeSuffix;
        }
}
