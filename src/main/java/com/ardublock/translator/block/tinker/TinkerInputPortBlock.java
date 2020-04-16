package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

/**
 *
 * @author User
 */
public class TinkerInputPortBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TinkerInputPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
	}

    /**
     *
     * @return
     * @throws SocketNullException
     */
    @Override
	public String toCode() throws SocketNullException
	{
		return codePrefix + label + codeSuffix;
	}
}