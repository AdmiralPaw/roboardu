package com.ardublock.translator.block.insectbot;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class InsectBotUtil
{

    /**
     *
     * @param translator
     */
    public static void setupEnv(Translator translator)
	{
		translator.addHeaderFile("Servo.h");
		translator.addHeaderFile("InsectBot.h");
		
		translator.addDefinitionCommand("InsectBot insect;");
	}
}
