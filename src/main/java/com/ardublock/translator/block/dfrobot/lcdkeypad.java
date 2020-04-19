package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class lcdkeypad extends TranslatorBlock {
	
    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public lcdkeypad(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0, "lcd.print( ", " );\n");
		translator.addHeaderFile("LiquidCrystal.h");
		translator.addDefinitionCommand("LiquidCrystal lcd(12, 11, 5, 4, 3, 2);");
		translator.addSetupCommand("lcd.begin(16, 2);");
		String ret = translatorBlock.toCode();
		return ret;
	}
	
}