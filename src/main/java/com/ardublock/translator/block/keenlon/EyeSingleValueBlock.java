package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class EyeSingleValueBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public EyeSingleValueBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("keenlon.h");		
		translator.addDefinitionCommand("Eye " + "eye" + ";");		
		String ret = "eye.singleValue(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof NumberBlock)
		{
			ret = ret + translatorBlock.toCode();
			ret = ret + ")";
			return codePrefix + ret + codeSuffix;
		}
		else
		{
			throw new BlockException(blockId, "must be a number");
		}
	}

}

