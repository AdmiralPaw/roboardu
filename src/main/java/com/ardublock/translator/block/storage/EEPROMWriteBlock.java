package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class EEPROMWriteBlock extends TranslatorBlock
{

	public EEPROMWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);

			String ret = "EEPROM.write( ";
			
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

			ret += tb.toCode();
			tb = this.getRequiredTranslatorBlockAtSocket(1);
			ret = "\t"+ret + " , " + tb.toCode() + " ) ;\n";
			
		return codePrefix + ret + codeSuffix;
	}
	
	private static void setupEEPROMEnvironment(Translator t)
	{
		t.addHeaderFile("EEPROM.h");
	}

}
