package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.DigitalInputBlock;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class TinkerButtonBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TinkerButtonBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
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
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof TinkerInputPortBlock)
		{
			String tkButtonName = "tkButton_" + translatorBlock.toCode();
			translator.addDefinitionCommand("TKButton "+  tkButtonName + "(" + translatorBlock.toCode() + ");");
			return tkButtonName + ".get()";
		}
		else
		{
			if (translatorBlock instanceof NumberBlock)
			{
				String number;
				number = translatorBlock.toCode();
				String setupCode = "pinMode( " + number + " , INPUT);";
				translator.addSetupCommand(setupCode);
				String ret = "digitalRead( ";
				ret = ret + number;
				ret = ret + ")";
				return codePrefix + ret + codeSuffix;
			}
			else
			{
				translator.addDefinitionCommand(DigitalInputBlock.ARDUBLOCK_DIGITAL_READ_DEFINE);
				String ret = "__ardublockDigitalRead(";
				
				ret = ret + translatorBlock.toCode();
				ret = ret + ")";
				return codePrefix + ret + codeSuffix;
			}
		}
	}
	
	
}
