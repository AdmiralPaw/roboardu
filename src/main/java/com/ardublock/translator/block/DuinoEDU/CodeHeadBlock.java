package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class CodeHeadBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public CodeHeadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
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
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/autre/EDU_CodeCache/");
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		translator.addHeaderFile("codeCache.h");
		String ret = translatorBlock.toCode();
		//ret=ret.substring(1);
		//ret=ret.replace(ret.substring(ret.length()-1),"");
		ret=ret+";\n";
		translator.addDefinitionCommand(ret);
		return "";
	}
}
